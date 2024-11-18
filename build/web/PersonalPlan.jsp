<%@page import="dto.Dish"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Page</title>
        <link href="Css/PlanStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <div>
            <!--<a class="header-text">Account</a>-->    
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>

        <% Account acc6 = (Account) session.getAttribute("LoginedAcc");
            if (acc6 != null) {
                ArrayList<Dish> list = (ArrayList<Dish>) request.getAttribute("DishPlan");
                if (list != null) {
        %>
        <div class="headtext">
            <h2>Your Personal Meal Plan</h2>
        </div>

        <div class="container">
            <table>
                <tr>
                    <th class="day">Day</th>
                    <th>Breakfast</th>
                    <th>Lunch</th>
                    <th>Dinner</th>
                </tr>

                <%
                    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                    int[] mealTypes = {1, 2, 3};
                    for (String day : days) {
                %>
                <tr>
                    <td class="day"><%= day%></td>
                    <%
                        for (int mealType : mealTypes) {
                    %>
                    <td>
                        <%
                            boolean mealFound = false;
                            for (Dish dish : list) {
                                if (dish.getDay().equalsIgnoreCase(day) && dish.getMeal() == mealType) {
                                    mealFound = true;
                        %>
                        <div>
                            <a href="MainController?action=recipes&dishid=<%= dish.getDishID()%>" style="font-size: 14px;"><%= dish.getDishName()%></a>
                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="dishid" value="<%= dish.getDishID()%>"/>
                                <input type="hidden" name="day" value="<%= dish.getDay()%>"/>
                                <input type="hidden" name="action" value="editplan"/>
                                <button type="submit" name="btaction" value="remove" class="button-remove"><img src="Picture/close.png"/></button>
                            </form>
                        </div>
                        <%
                                }
                            }
                            if (!mealFound) {
                        %>
                        <div></div>
                        <%
                            }
                        %>
                    </td>
                    <%
                        }
                    %>
                </tr>
                <% } %>
            </table>
        </div>
        <%
        } else {
        %>
        <p class="message">Oops! It looks like your plan is empty. Please add dishes to make a plan.</p>
        <%
            }
        } else {
        %>
        <p class="message">You need to log in to use this feature.</p>
        <div class="login-form">
            <form action="MainController" method="post">
                <button type="submit" name="action" value="login">Log in</button>
            </form>
        </div>
        <%
            }
        %>

        <p class="message"><%= (request.getAttribute("er") != null) ? request.getAttribute("er") : ""%></p>
        <p class="message"><%= (request.getAttribute("error5") != null) ? request.getAttribute("error5") : ""%></p>
    </body>
</html>
