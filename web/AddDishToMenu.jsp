<%-- 
    Document   : AddDishToMenu
    Created on : Jun 24, 2024, 4:31:38 PM
    Author     : nvhoa
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dto.Dish"%>
<%@page import="java.util.List"%>
<%@page import="dao.DishDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/adddishtomenu.css"/>
    </head>
    <body>
        <%
            String menu = (String) request.getParameter("menu");
        %>
        <div class="search-bar row">
            <h3 class="col-md-12" style="padding-top: 20px">Search Dishes</h3>
            <%
                if (menu != null && menu.equalsIgnoreCase("update")) {
            %>
            <a href="MainController?action=updatemenu" class="col-md-2">My Menu</a>
            <%
            } else {
            %>
            <a href="MainController?action=createnewmenu" class="col-md-2">My Menu</a>
            <%
                }
            %>
            <form action="MainController?action=searchdish" method="post" class="col-md-10"">
                <input type="text" name="searchKey" placeholder="Search dish name" value="${sessionScope.searchDishKey}">
                <input type="submit" value="Search" name="button" style="padding: 3px 5px;">
                <input type="submit" value="Show All Dish" name="button" style="padding: 3px 5px;">
            </form>
        </div>
        <%
            String ms1 = (String) request.getAttribute("NODISH");
            if (ms1 != null) {
        %>
        <h3><%= ms1%></h3>
        <%
        } else {
        %>
        <%
            // Lấy danh sách món ăn từ cơ sở dữ liệu
            DishDao dd = new DishDao();
            List<Dish> dList = (List<Dish>) request.getAttribute("dishList");

            if (dList.isEmpty()) {
        %>
        <h1 style="text-align: center; font-weight: bold; font-style: italic; color: orange" >There is no Dish</h1>
        <%
        } else {

            List<String> selectedDish = (List<String>) session.getAttribute("selectedDish");
        %>
        <div class="dish-container">
            <%
                for (Dish dish : dList) {
                    boolean check = false;
                    if (selectedDish != null && !selectedDish.isEmpty()) {
                        check = selectedDish.contains(String.valueOf(dish.getDishID()));
                    }

            %>
            <div class="dish-card">
                <%        if (menu != null && menu.equalsIgnoreCase("update")) {
                %>
                <form action="MainController?action=adddishtomenudb" method="post">
                    <%
                    } else {
                    %>
                    <form action="MainController?action=adddishtomenu" method="post">
                        <%
                            }
                        %>
                        <img src="<%= dish.getImage() %>">
                        <p><%= dish.getDishName()%></p>
                        <p><%= dish.getDishType()%></p>
                        <p style="color: orange"><%= dd.getCurrentPriceByDishID(dish.getDishID()) %>$</p>
                        <input type="hidden" name="id" value="<%= dish.getDishID()%>">
                        <%
                            if (check) {
                        %>
                        <input type="submit" value="Added" readonly>
                        <%
                        } else {
                        %>
                        <input type="submit" value="Add to menu">
                        <%
                            }
                        %>
                    </form>
            </div>
            <%
                        }
                    }
                }
            %>
        </div>
        <div class="endpage"></div>
    </body>
</html>
