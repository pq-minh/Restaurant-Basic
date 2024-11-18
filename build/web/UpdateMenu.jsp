<%-- 
    Document   : JustifyMenu
    Created on : Jun 25, 2024, 4:17:55 PM
    Author     : nvhoa
--%>

<%@page import="dto.Menu"%>
<%@page import="dao.MenuDao"%>
<%@page import="dao.DishDao"%>
<%@page import="dto.Dish"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/updatemenu.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        

        <div class="cont1">
            <%
                MenuDao md = new MenuDao();
                Menu menu = (Menu) request.getAttribute("menuInf");
                if (menu != null) {
            %>
            <div class="cont1-sub1">
                <h1>Menu Information</h1>
                <h3>Menu Name: <%= menu.getMenuName()%></h3>
                <h3>Start Date: <%= menu.getMenuStartDate()%></h3>
                <h3>End Date: <%= menu.getMenuEndDate()%></h3>
            </div>
            <%
                }
            %>
            <%
                String ms = (String) request.getAttribute("NOMENU");
                if (ms == null) {
            %>
            <div class="cont1-sub2">
                <form action="MainController?action=updatemenu" method="post">
                    <p>Rename menu</p>
                    <input type="text" name="menuname" placeholder="Enter menu name">
                    <p>Change startdate</p>
                    <input type="text" name="startdate"placeholder="Start Date">
                    <p>Change enddate</p>
                    <input type="text" name="enddate" placeholder="End Date"><br>
                    <input type="hidden" value="true" name="changemenuinf">
                    <input style="padding: 3px 10px;" type="submit"><br>
                    <a style="text-decoration: underline; font-family: 'Times New Roman', Times, serif;" href="MainController?action=adddishtomenu&menu=update">Add Dish To Menu</a>
                </form>
            </div>
        </div>

        <div class="row cont2">
            <%        DishDao dd = new DishDao();
                List<Dish> dL = (List<Dish>) request.getAttribute("dishList");

                if (dL != null) {
                    for (Dish d : dL) {
           %>
           <div class="col-md-4 dish-card" style="width: 300px; padding: 0px 0px 20px 0px;">
               <img src="<%= d.getImage() %>">
                <p><%= d.getDishName()%></p>
                <p><%= (d.getDishType() == 1) ? "Vegetarian" : ""%></p>
                <p><%= (d.getDishType() == 2) ? "Vegan" : ""%></p>
                <p><%= (d.getDishType() == 3) ? "Low-Carb" : ""%></p>
                <p><%= (d.getDishType() == 4) ? "Low-Fat" : ""%></p>
                <p><%= (d.getDishType() == 5) ? "High-Carb" : ""%></p>
                <p><%= (d.getDishType() == 6) ? "High-Fat" : ""%></p>
                <p><%= (d.getDishType() == 7) ? "Savory" : ""%></p>
                <p style="color: orange"><%= dd.getCurrentPriceByDishID(d.getDishID()) %>$</p>
                <form action="MainController?action=updatemenu" method="post">
                    <input type="hidden" value="<%= d.getDishID()%>" name="dishID">
                    <input type="hidden" value="true" name="removedish">
                    <input type="submit" value="Remove dish">
                </form>
            </div>
            <%
                    }
                }
                if (dL.isEmpty()) {
                %>
                <h3 style="background-color: #33aba0; padding: 15px 200px; border-radius: 10px; color: white; font-weight:  bold">You haven't add any dish to this Menu yet.</h3>
                <%
                }
            %>
        </div>
        <%
        } else {
        %>
        <h3><%= ms%></h3>
        <%
            }
        %>
        <div class="endpage"></div>
    </body>
</html>
