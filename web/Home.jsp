<%-- 
    Document   : Home
    Created on : Jun 3, 2024, 8:34:13 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/home.css" rel="stylesheet">
        <link href="Css/bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <div>
            <a class="header-text">Welcome to LaylaFood</a>   
            <form action="MainController" method="post" class="header-button" >
                <button type="submit" name="action" value="menu">Menu</button>
            </form>
            <img src="Picture/vegetables-fresh.jpg" class="imghearder" />
        </div>

        <h1 style="text-align: center;">How it work</h1>
        <div class="row container1">
            <div class="col-md-4">
                <img src="Picture/restaurant.png" class="imgmain"/>
                <div class="item1">
                <h3>Choose Your Favorite</h3>
                <p>Choose your favorite meals and order online or by phone. It's easy to customize your order.</p>
                </div>
            </div>
            <div class="col-md-4">
                <img src="Picture/fast-delivery.png" class="imgmain"/>
                <div class="item1">
                <h3>We Deliver Your Meals</h3>
                <p>We prepared and delivered meals arrive at your door.</p>
                </div>
            </div>
            <div class="col-md-4">
                <img src="Picture/meal.png" class="imgmain"/>
                <div class="item1">
                <h3>Eat And Enjoy</h3>
                <p>No shooping, no cooking, no counting and no cleaning. Enjoy your healthy meals with your family.</p>
                </div>
            </div>
        </div>

        <%@include file="Footer.jsp" %>
    </body>
</html>
