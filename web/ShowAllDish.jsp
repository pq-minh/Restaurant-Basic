<%-- 
    Document   : JustifyDish
    Created on : Jun 27, 2024, 9:53:28 AM
    Author     : nvhoa
--%>

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
        <link rel="stylesheet" href="Css/showalldish.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div class="cont1" style="margin-top: 70px;">
            <h1>Update Dish</h1>
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Dish Name</th>
                        <th>Dish Type</th>
                        <th>Meal</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        String[] dishType = {"Vegetarian", "Vegan", "Low-Carb", "Low-Fat", "High-Carb", "High-Fat", "Savory"};
                        String[] meal = {"Breakfast", "Lunch", "Dinner"};
                        DishDao dd = new DishDao();
                        List<Dish> adL1 = (List<Dish>) request.getAttribute("adList1");
                        if (adL1 != null) {
                            int c = 0;
                            for (Dish d : adL1) {
                                c++;
                    %>

                    <tr>
                        <td>
                            <%= c%> 
                        </td>
                        <td>
                            <p><%= d.getDishID()%></p>
                        </td>
                        <td>
                            <p><%= d.getDishName()%></p>
                        </td>
                        <td>
                            <p><%= dishType[d.getDishType() - 1]%></p>
                        </td>
                        <td>
                            <p><%= meal[d.getMeal() - 1]%></p>
                        </td>
                        <td>
                            <p><%= dd.getCurrentPriceByDishID(d.getDishID())%></p>
                        </td>
                        <td>
                            <p><%= (d.getStatus() == 1) ? "Available" : "Unavailable"%></p>
                        </td>
                        <td>
                            <form action="MainController?action=updatedish" method="post">
                                <input type="hidden" value="<%= d.getDishID()%>" name="dishID">
                                <input type="submit" value="Delete">
                                <input type="hidden" value="true" name="delete">
                            </form>
                        </td>
                        <td>
                            <form action="MainController?action=updatedish" method="post">
                                <input type="hidden" value="<%= d.getDishID()%>" name="dishID">
                                <input type="submit" value="Apply">
                                <input type="hidden" value="true" name="apply">
                            </form>
                        </td>
                        <td>
                            <form action="MainController?action=updatedish" method="post">
                                <input type="hidden" value="<%= d.getDishID()%>" name="dishID">
                                <input type="submit" value="Update">
                                <input type="hidden" value="true" name="update">
                            </form>
                        </td> 
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>
        <div class="endpage"></div>
    </body>
</html>
