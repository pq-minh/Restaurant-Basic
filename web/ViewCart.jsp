<%-- 
    Document   : ViewCart
    Created on : Jun 24, 2024, 4:17:01 PM
    Author     : Asus
--%>

<%@page import="dao.AccountDao"%>
<%@page import="java.util.Date"%>
<%@page import="dto.Dish"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/menuStyle.css" rel="stylesheet"/>
        <link href="Css/cartStyle.css" rel="stylesheet"/>
        <link href="Css/bootstrap.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <!--<a class="header-text">Menu</a>-->
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <%            HashMap<Dish, Integer> cart = (HashMap) session.getAttribute("cart");
            Date lastAddedTime = (Date) session.getAttribute("lastAddedTime");
            if (cart != null) {
                //request.getRequestDispatcher("GetAllItemServlet").forward(request, response);
                float total = 0;
        %>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <table class="table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Dish</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Dish di : cart.keySet()) {
                                    int quantity = cart.get(di);
                                    total = total + quantity * di.getDishPrice();
                                    float subtotal = 0;
                            %>
                            <tr>
                                <td><img src="<%= di.getImage()%>" style="width: 100px; height: 100px; border-radius: 5px; "/></td>
                                <td><%= di.getDishName()%></td>
                                <td><%= di.getDishPrice()%> VND</td>
                                <td><input type="number" value="<%= quantity%>" min="1" max="25" name="txtquantity" class="form-control"/></td>
                                <td><%= subtotal = quantity * di.getDishPrice()%></td>
                                <td>
                                    <form action="MainController" method="post">
                                        <input type="hidden" name="dishid" value="<%= di.getDishID()%>"/>
                                        <input type="hidden" name="action" value="btactionincart"/>
                                        <button type="submit" name="btaction" value="remove" class="button-remove"><img src="Picture/close.png"/></button>
                                    </form>
                                </td>
                            </tr>

                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-4">
                    <div class="user-info">
                        <%
                            Account acc2 = (Account) session.getAttribute("LoginedAcc");
                            if (acc2 != null) {
                                AccountDao ad = new AccountDao();
                                Account acc7 = ad.getUser(acc2.getEmail());
                        %>
                        <p><strong>Name:</strong> <%= acc7.getFullName()%></p>
                        <p><strong>Email:</strong> <%= acc7.getEmail()%></p>
                        <p><strong>Phone:</strong> <%= acc7.getPhoneNumber()%></p>
                        <p class="address"><strong>Address:</strong> <%= (acc7.getAddress() != null) ? acc7.getAddress() + ", " : ""%> <%= (acc7.getDistrict() != null) ? "Quận " + acc7.getDistrict() + ", " : ""%> <%= (acc7.getCity() != null) ? acc7.getCity() : ""%></p>
                        <%
                            }
                        %>
                    </div>

                    <div class="cart-summary">
                        <p>Tổng tiền thanh toán: <%= String.format("%.3f", total)%> VND</p>
                        <p>Ngày đặt hàng : <%= lastAddedTime != null ? lastAddedTime.toString() : "N/A"%></p>
                        <form action="MainController" method="post">
                            <button type="submit" name="action" value="buy" class="btn button1">Thanh toán</button>
                        </form>
                    </div>

                </div>
            </div>
        </div>
        <p><%= (request.getAttribute("Error1") != null) ? request.getAttribute("Error1") : ""%></p>
        <a><%= (request.getAttribute("Error2") != null) ? request.getAttribute("Error2") : ""%></a>
        <%
            String addbutton = (String) request.getAttribute("Error2");
            if (addbutton != null) {
        %>
        <form action="MainController" method="post">
            <button type="input" name="action" value="updateprofilecustomerpage" class="button-add">Update profile</button>
        </form>
        <%
            }
        %>

        <%
        } else {
        %>
        <div class="item">
            <p> <img src="Picture/package.png" class="img-box" /></p>
            <h3>Oops! Your cart is empty!</h3>
            <p>Look like you haven't added anything to your cart yet</p>
            <form action="MainController" method="post" class="button-menu">
                <button type="submit" name="action" value="menu">Shop now</button>
            </form>
        </div>

        <%}
        %>

    </body>
</html>
