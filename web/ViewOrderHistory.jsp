<%-- 
    Document   : ViewOrderHistory
    Created on : Jul 12, 2024, 3:21:16 PM
    Author     : Asus
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.OrderDetail"%>
<%@page import="dto.Dish"%>
<%@page import="dao.DishDao"%>
<%@page import="dto.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
        <link href="Css/OrderHistoryStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>

        <%
            Account acc8 = (Account) session.getAttribute("LoginedAcc");
            if (acc8 != null) {
                Map<Integer, ArrayList<OrderDetail>> ordersMapp = (Map<Integer, ArrayList<OrderDetail>>) request.getAttribute("OrdersMap");
                if (ordersMapp != null && !ordersMapp.isEmpty()) {
                    DishDao dishDao = new DishDao();
                    for (Integer orderId : ordersMapp.keySet()) {
                        ArrayList<OrderDetail> details = ordersMapp.get(orderId);
                        float total = 0;
        %>
        <div class="order-history">
<!--            <h3>Order ID: <%= orderId %></h3>-->
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Dish Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (OrderDetail odt : details) {
                            Dish dish = dishDao.getDishByDishID(odt.getDishID());
                            if (dish != null) {
                                float subtotal = odt.getQuantity() * odt.getPrice();
                                total += subtotal;
                    %>
                    <tr>
                        <td><img src="<%= dish.getImage() %>" alt="Dish Image"/></td>
                        <td><%= dish.getDishName() %></td>
                        <td><%= String.format("%.3f", odt.getPrice()) %>đ</td>
                        <td><%= odt.getQuantity() %></td>
                        <td><%= String.format("%.3f", subtotal) %>đ</td>
                        <td>
                            <%
                                String status = "Processing";
                                if (odt.getStatus() == 2) {
                                    status = "Shipping";
                                } else if (odt.getStatus() == 3) {
                                    status = "Delivered";
                                } else if (odt.getStatus() == 4) {
                                    status = "Cancelled";
                                }
                            %>
                            <%= status %>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td colspan="4" style="text-align: right;"><strong>Total:</strong></td>
                        <td colspan="2"><strong><%= String.format("%.3f", total) %>đ</strong></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <%
                    }
                } else {
        %>
        <div class="item11">
            <p><img src="Picture/grocery-cart.png" class="img-box"/></p>
            <h3>Oops! Order history is empty</h3>
            <p>It seems like you've never bought food here</p>
            <form action="MainController" method="post" class="button-menu">
                <button type="submit" name="action" value="menu">Shop now</button>
            </form>
        </div>
        <%
                }
            } else {
                request.getRequestDispatcher("MainController?action=loginpage").forward(request, response);
            }
        %>
    </body>
</html>
