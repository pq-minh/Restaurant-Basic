<%-- 
    Document   : ManageOrder
    Created on : Jun 17, 2024, 3:42:29 PM
    Author     : nvhoa
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.DishDao"%>
<%@page import="dto.Dish"%>
<%@page import="dto.OrderDetail"%>
<%@page import="dto.Order"%>
<%@page import="dao.OrderDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="dao.AccountDao"%>
<%@page import="dto.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Accounts</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/manageorder.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div class="header">
            <h1>Manage Order By District</h1>
        </div>

        <div class="search-container">
            <form action="MainController?action=manageorder" method="post">
                <input type="text" name="searchKey" value="${sessionScope.searchOrderKey}" placeholder="Search...">
                <input type="submit" value="Search" name="button">
                <input type="submit" value="Show All" name="button">
            </form>
        </div>

        <div class="row">
            <%
                DishDao dd = new DishDao();
                OrderDao od = new OrderDao();
                String ms = (String) request.getAttribute("NOTFOUND");
                if (ms != null) {
            %>
            <h1>${requestScope.NOTFOUND}</h1>
            <%
            } else {
                List<Account> listA = (List<Account>) session.getAttribute("accountList");
                Map<String, List<Account>> groupAccount = od.groupAccountByDistrict(listA);
            %>
            <%
                for (Map.Entry<String, List<Account>> entry : groupAccount.entrySet()) {
                    String district = entry.getKey();
                    List<Account> account = entry.getValue();
            %>
            <div class="district-header">
                <h2>Quận <%= district%></h2>
                <form action="MainController?action=manageorder" method="post">
                    <input type="hidden" value="<%= district%>" name="district">
                    <input type="date" name="dateToSearch">
                    <input type="submit">
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Order Date</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>Dish Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Account acc : account) {
                            List<Order> oList = od.getOrderByAccountID(acc.getAccountID() );         // lấy order từ account

                            String dts = (String) session.getAttribute("dateToSearch");
                            String dis = (String) session.getAttribute("district");
                            if (dis != null && dis.equalsIgnoreCase(district)) {
                                if (dts != null && !dts.isEmpty()) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                                    oList = od.searchOrderDate(oList, sdf.parse(dts));
                                }
                            }

                            for (Order o1 : oList) {
                                List<OrderDetail> odL = od.getOrderDetailsByOrderID(o1.getOrderID());           // lấy orderdetail từ order
                                for (OrderDetail o2 : odL) {
                                    Dish d = dd.getDishByDishID(o2.getDishID());            // lấy dish từ orderdetail
                    %>
                    <tr>
                        <td><%= acc.getFullName() %></td>
                        <td><%= acc.getEmail()%></td>
                        <td><%= acc.getPhoneNumber()%></td>
                        <td><%= o1.getOrderDate()%></td>
                        <td><%= acc.getAddress()%></td>
                        <td><%= acc.getCity()%></td>
                        <td><%= d.getDishName()%></td>
                        <td><%= o2.getPrice()%></td>
                        <td><%= o2.getQuantity()%></td>
                        <td><%= o1.getTotal()%></td>
                        <td>
                            <%
                                if (o2.getStatus() == 1) {
                            %>
                            <p>Pending</p>
                            <%
                            } else if (o2.getStatus() == 4) {
                            %>
                            <p style="color: orange;">Cancelled</p>
                            <%
                            } else if (o2.getStatus() == 2) {
                            %>
                            <p style="color: green;">In Delivery</p>
                            <%
                            } else if (o2.getStatus() == 3) {
                            %>
                            <p style="color: green;">Delivered</p>
                            <%
                                }
                            %>
                        </td>
                        <td>
                            <%
                                if (o2.getStatus() == 1) {
                            %>
                            <a class="link" href="MainController?action=delivery&detailID=<%= o2.getDetailID()%>" onclick="confirmDeliver()">Deliver</a>
                            <a class="link" href="MainController?action=cancelorder&detailID=<%= o2.getDetailID()%>" onclick="confirmCancel()">Cancel</a>
                            <%
                            } else if (o2.getStatus() != 4) {
                            %>
                            <a class="link" href="MainController?action=cancelorder&detailID=<%= o2.getDetailID()%>" onclick="confirmCancel()">Cancel</a>
                            <%
                                }
                            %>
                        </td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                </tbody>
            </table>
            <hr>
            <%
                    }
                }
            %>
        </div>

        <script>
            function confirmDeliver() {
                return confirm("Are you sure you want to deliver this order?");
            }

            function confirmCancel() {
                return confirm("Are you sure you want to cancel this order?");
            }
        </script>
    </body>
</html>

