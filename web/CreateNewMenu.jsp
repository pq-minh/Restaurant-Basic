<%-- 
    Document   : ShowAddedMenu
    Created on : Jun 24, 2024, 6:35:36 PM
    Author     : nvhoa
--%>

<%@page import="dto.Dish"%>
<%@page import="dao.DishDao"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/createnewmenu.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <%
            DishDao dd = new DishDao();
            List<String> mL = (List<String>) session.getAttribute("menuList");
            List<String> selectedDish = (List<String>) session.getAttribute("selectedDish");
            String ms1 = (String) request.getAttribute("SUCCESS");
            String ms2 = (String) request.getAttribute("FAIL");
        %>
        <div class="menu-inf">
            <h1>Menu Information</h1>
            <h3>Menu Name: <%= mL.get(0)%></h3>
            <h3>Start Date: <%= mL.get(1)%></h3>
            <h3>End Date: <%= mL.get(2)%></h3>
            <form action="MainController?action=createnewmenu" method="post">
                <input type="hidden" value="true" name="createmenu">
                <input type="submit" value="Create Menu" style="padding: 3px 10px;">
            </form>
            <a style="text-decoration: underline; font-family: 'Times New Roman', Times, serif;" href="MainController?action=adddishtomenu">Add New Dish</a>
            <%
                if (ms1 != null) {
            %>
            <h3 style="color: orange; font-style:  italic;"><%= ms1%></h3>
            <%
            } else if (ms2 != null) {
            %>
            <h3 style="color: orange; font-style:  italic;"><%= ms2%></h3>
            <%
                }
            %>

        </div>
        <div class="dish-container">
            <%
                if (selectedDish != null) {
                    for (String d : selectedDish) {
                        Dish dish = dd.getAvailableDishByDishID(Integer.parseInt(d));
            %>
            <div class="dish-card">
                <img src="<%= dish.getImage() %>"> 
                <p><%= dish.getDishName()%></p>
                <p><%= dish.getDishType()%></p>
                <p style="color: orange"><%= dd.getCurrentPriceByDishID(dish.getDishID()) %>$</p>
                <form action="MainController?action=createnewmenu" method="post">
                    <input type="hidden" name="delete" value="true">
                    <input type="hidden" name="id" value="<%= d%>">
                    <input type="submit" value="Remove">
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="endpage"></div>
    </body>
</html>
