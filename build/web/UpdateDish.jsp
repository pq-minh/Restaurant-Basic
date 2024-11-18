<%-- 
    Document   : JustifyDish
    Created on : Jun 27, 2024, 10:29:10 AM
    Author     : nvhoa
--%>

<%@page import="dto.Ingredient"%>
<%@page import="java.util.List"%>
<%@page import="dao.IngredientDao"%>
<%@page import="dto.Dish"%>
<%@page import="dao.DishDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/updatedish.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div class="row" style="margin-top: 80px;">
            <%
                IngredientDao id = new IngredientDao();
                DishDao dd = new DishDao();
                String dishID = (String) session.getAttribute("dishID");
                Dish dish = dd.getDishByDishID(Integer.parseInt(dishID));
    %>

            <div class="col-md-4 cont1" style="width: fit-content; padding: 0px 50px 20px 50px; margin: 20px 20px 0px 20px;" >
                <div class="cont1-sub1">
                    <h2>Dish information</h2>
                    <img src="<%= dish.getImage() %>">
                    <p>Dish Name: <%= dish.getDishName()%></p>
                    <p>Current Price: <%= dd.getCurrentPriceByDishID(Integer.parseInt(dishID))%></p>
                    <%
                        String[] dishtype = {"Vegetarian", "Vegan", "Low-Carb", "Low-Fat", "High-Carb", "High-Fat", "Savory"};
                        String[] meal = {"Breakfast", "Lunch", "Dinner"};
                    %>
                    <p>Type: <%= dishtype[dish.getDishType() - 1]%></p>
                    <p>Meal: <%= meal[dish.getMeal() - 1]%></p>
                    <p>Status: <%= (dish.getStatus() == 1) ? "Available" : "Unavailable"%></p>
                </div>

                <div class="cont1-sub2">
                    <form action="MainController?action=updatedish" method="post">
                        <p>Change Dish Name</p>
                        <input type="text" name="dishname" placeholder="Enter dish name" >
                        <p>Dish Price</p>
                        <input type="number" name="dishprice" step="0.001" min="0" placeholder="Price">
                        <p>Dish Type</p>
                        <select name="dishtype" >
                            <option value="0">none</option>
                            <option value="1">Vegetarian</option>
                            <option value="2">Vegan</option>
                            <option value="3">Low-Carb</option>
                            <option value="4">Low-Fat</option>
                            <option value="5">High-Carb</option>
                            <option value="6">High-Fat</option>
                            <option value="7">Savory</option>
                        </select>
                        <p>Description</p>
                        <textarea name="description" rows="7" ><%= dish.getDescription() %></textarea><br>
                        <label style="margin-right: 5px;">Breakfast<input type="radio" name="meal" value="1"></label>
                        <label style="margin-right: 5px;">Lunch<input type="radio" name="meal" value="2"></label>
                        <label style="margin-right: 5px;">Dinner<input type="radio" name="meal" value="3"></label><br>
                        <a style="text-decoration: underline; font-family: 'Times New Roman', Times, serif; font-size: 20px" href="MainController?action=albumdish&update=true">Change dish image</a><br>
                        <input type="hidden" name="changeinf" value="true">
                        <input type="submit" value="Submit">
                    </form>
                </div>
            </div>
            <div class="col-md-8 cont2">
                <%
                    List<Ingredient> ingL = id.getAllIngredientByDishID(Integer.parseInt(dishID));
                    if (ingL != null) {
                %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>In Stock</th>
                            <th>Change quantity</th>
                            <th>Remove ingredient</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Ingredient i : ingL) {
                        %>
                        <tr>
                            <td>
                                <p><%= i.getIngredientID()%></p>
                            </td>
                            <td>
                                <p><%= i.getIngredientName()%></p>
                            </td>
                            <td>
                                <p><%= i.getQuantity()%></p>
                            </td>
                            <td>
                                <p><%=  id.getIngredientQuantityInStock(i.getIngredientID())%></p>
                            </td>
                            <td>
                                <form action="MainController?action=updatedish" method="post">
                                    <input type="number" name="quantity" min="0" step="1">
                                    <input type="hidden" name="ingID" value="<%= i.getIngredientID()%>">
                                    <input type="hidden" value="true" name="changequantity">
                                    <input type="submit">
                                </form>
                            </td>
                            <td>
                                <form action="MainController?action=updatedish" method="post">
                                    <input type="hidden" name="ingID" value="<%= i.getIngredientID()%>">
                                    <input type="hidden" value="true" name="removeing">
                                    <input type="submit" value="Remove">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table> 
                <%
                    }
                %>

            </div>

        </div>
        <hr>
        <div class="cont3">
            <h1>Add Ingredient To Dish</h1> 
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>In Stock</th>
                        <th>Add Ingredient</th>
                    </tr>
                </thead>
                <%
                    List<Ingredient> aiL = (List<Ingredient>) request.getAttribute("aiList");
                    if (aiL != null) {
                        for (Ingredient i : aiL) {
                %>
                <tbody>
                    <tr>
                        <td>
                            <p><%= i.getIngredientID()%></p>
                        </td>
                        <td>
                            <p><%= i.getIngredientName()%></p>
                        </td>
                        <td>
                            <p><%= id.getIngredientQuantityInStock(i.getIngredientID())%></p>
                        </td>
                        <td>
                            <form action="MainController?action=updatedish" method="post">
                                <input type="number" name="quantity" min="0" step="1" required="">
                                <input type="hidden" name="ingID" value="<%= i.getIngredientID()%>">
                                <input type="hidden" name="adding" value="true">
                                <input type="submit">
                            </form>
                        </td>
                    </tr>
                </tbody>
                <%
                        }
                    }
                %>
            </table>
        </div>
        <div class="endpage"></div>
    </body>
</html>
