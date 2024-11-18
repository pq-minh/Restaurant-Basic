<%-- 
    Document   : ViewRecipes
    Created on : Jul 6, 2024, 12:40:21 AM
    Author     : Asus
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dto.Ingredient"%>
<%@page import="dto.Dish"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/bootstrap.css" rel="stylesheet"/>
        <link href="Css/recipesStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <div class="container">
            <% Dish dish = (Dish) request.getAttribute("dish");
                if (dish != null) {
            %>
            <div class="row">
                <div class="col-md-6">
                    <img src="<%= dish.getImage()%>" class="img1"/>
                </div>
                <div class="col-md-6">
                    <h2 style="font-size: 50px; font-weight: bold;"><%= dish.getDishName()%></h2>
                </div>
            </div>
            <% } else { %>
            <p>Trống</p>
            <% } %>
            <p class="textavr">Thành phần</p>
            <div class="ingredient-list">
                <% ArrayList<Ingredient> list11 = (ArrayList) request.getAttribute("ingredient");
                    if (list11 != null) {
                        for (Ingredient ingre : list11) {
                %>
                <div class="ingredient">
                    <img src="<%= ingre.getImage()%>"/>
                    <p>Ingredient: <%= ingre.getIngredientName()%></p>
                    <p>Quantity: <%= ingre.getQuantity()%></p>
                </div>
            <% }
            %>
            </div>
            <p class="textavr">Hướng dẫn nấu</p>
            <p><%= dish.getDescription() %></p>
            <%
} else { %>
            <p>Trống nha cưng</p>
            <% }%>

        </div>
    </body>
</html>
