<%-- 
    Document   : ThankYou
    Created on : Jul 3, 2024, 9:58:11 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/menuStyle.css" rel="stylesheet"/>
        <link href="Css/cartStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <%            String msg1 = (String) request.getAttribute("msgfromsave");
            String msg2 = (String) request.getAttribute("updatesucess");
            String msg3 = (String) request.getAttribute("thankregister");
            if (msg1 != null && msg1.equalsIgnoreCase("thankforbuy")) {
        %>
        <div class="item">
            <p> <img src="Picture/package1.png" class="img-box" /></p>
            <h3>Thank you for your purchase</h3>
            <p>Your order is being packaged and awaiting shipping</p>
            <form action="MainController" method="post" class="button-menu">
                <button type="submit" name="action" value="menu">Shop now</button>
            </form>
        </div>
        <%
        } else if (msg2 != null && msg2.equalsIgnoreCase("updatesucess")) {
        %>
        <div class="item">
            <p> <img src="Picture/curriculum-vitae.png" class="img-box" /></p>
            <h3>Update profile success</h3>
            <p>Go shopping right now</p>
            <form action="MainController" method="post" class="button-menu">
                <button type="submit" name="action" value="menu">Shop now</button>
            </form>
        </div>
        <%
            } else if (msg3 != null && msg3.equalsIgnoreCase("thankregister")) {
        %>
        <div class="item">
            <p> <img src="Picture/curriculum-vitae.png" class="img-box" /></p>
            <h3>Regiter success</h3>
            <p>Log in right now</p>
            <form action="MainController" method="post" class="button-menu">
                <button type="submit" name="action" value="loginpage">Log in</button>
            </form>
        </div>
        <%
            }else {
                request.getRequestDispatcher("MainController?action=home").forward(request, response);
            }
        %>
    </body>
</html>
