<%-- 
    Document   : AddNewDishToBD
    Created on : Jun 26, 2024, 4:06:16 PM
    Author     : nvhoa
--%>

<%@page import="dao.IngredientDao"%>
<%@page import="dto.Ingredient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/createnewdish.css"/>
        <style>
            .row{
                margin-top: 70px;
            }
            .col-md-4{
                margin-top: 78px;
            }
            .cont1{
                margin-top: 78px;
            }
            .cont1 form p{
                margin-left: 18px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div class="row">
            <%
                List<String> dishInf = (List<String>) session.getAttribute("dishInf");
                String s1 = (String) request.getAttribute("SUCCESS1");
                String s2 = (String) request.getAttribute("SUCCESS2");
                String f = (String) request.getAttribute("FAIL");
            %>
            <div class="cont1 col-md-4" style="margin-top: 78px; width: fit-content; padding: 20px 30px; margin-left: 20px; margin-right: 20px;">
                <form action="MainController?action=createnewdish" method="post">
                    <%
                        String img = (String) session.getAttribute("imgname");
                        if (img != null) {
                    %>
                    <img src="Picture/<%= img%>">     
                    <%
                        }
// name price type des meal
                    %>
                    <p>Dish Name</p>
                    <input type="text" name="dishname" required="" placeholder="Enter dish name" value="<%= (dishInf != null) ? dishInf.get(0) : ""%>">
                    <p>Dish Price</p>
                    <input type="number" name="dishprice" step="0.001" min="0" required="" placeholder="Price" value="<%= (dishInf != null) ? dishInf.get(1) : ""%>">
                    <p>Dish Type</p>
                    <select name="dishtype" required>
                        <%
                            String dt = null;
                            if (dishInf != null) {
                                dt = dishInf.get(2);
                            }
                        %>
                        <option value="1" <%= (dt != null && dt.equals("1")) ? "selected" : ""%>>Vegetarian</option>
                        <option value="2" <%= (dt != null && dt.equals("2")) ? "selected" : ""%>>Vegan</option>
                        <option value="3" <%= (dt != null && dt.equals("3")) ? "selected" : ""%>>Low-Carb</option>
                        <option value="4" <%= (dt != null && dt.equals("4")) ? "selected" : ""%>>Low-Fat</option>
                        <option value="5" <%= (dt != null && dt.equals("5")) ? "selected" : ""%>>High-Carb</option>
                        <option value="6" <%= (dt != null && dt.equals("6")) ? "selected" : ""%>>High-Fat</option>
                        <option value="7" <%= (dt != null && dt.equals("7")) ? "selected" : ""%>>Savory</option>
                    </select>
                    <p>Description</p>
                    <textarea name="description" rows="7" required><%= (dishInf != null) ? dishInf.get(3) : ""%></textarea><br>
                    <label style="margin-right: 5px;">
                        Breakfast
                        <input type="radio" name="meal" value="1" <%= (dishInf != null && dishInf.get(4).equals("1")) ? "checked" : ""%>>
                    </label>
                    <label style="margin-right: 5px;">
                        Lunch
                        <input type="radio" name="meal" value="2" <%= (dishInf != null && dishInf.get(4).equals("2")) ? "checked" : ""%>>
                    </label>
                    <label style="margin-right: 5px;">
                        Dinner
                        <input type="radio" name="meal" value="3" <%= (dishInf != null && dishInf.get(4).equals("3")) ? "checked" : ""%>>
                    </label><br>

                    <a style="text-decoration: underline; font-family: 'Times New Roman', Times, serif; font-size: 20px" href="MainController?action=albumdish">Add image to dish</a><br>
                    <input type="submit" value="Save" name="button">
                    <input type="submit" value="Create Dish" name="button">
                    <%
                        if (s1 != null) {
                    %>
                    <h3><%= s1%></h3>
                    <%
                    } else if (s2 != null) {
                    %>
                    <h3><%= s2%></h3>
                    <%
                    } else if (f != null) {
                    %>
                    <h3><%= f%></h3>
                    <%
                        }
                    %>
                </form> 
            </div>

            <div class="cont2 col-md-8">
                <h1>Add Ingredient To Dish</h1> 
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Quantity In Stock</th>
                            <th>Quantity In Dish</th>
                            <th></th>  
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            List<Ingredient> iL = (List<Ingredient>) request.getAttribute("allIngList");
                            if (iL != null) {
                                for (Ingredient i : iL) {
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
                                <%
                                    IngredientDao id = new IngredientDao();
                                    List<Ingredient> isL = (List<Ingredient>) session.getAttribute("ingSessionList");
                                    if (isL != null && !isL.isEmpty()) {
                                        boolean c = false;
                                        for (Ingredient y : isL) {
                                            if (y.getIngredientID() == i.getIngredientID()) {
                                                c = true;
                                %>
                                <p><%= y.getQuantity()%></p>
                                <%
                                        }
                                    }
                                    if (c == false) {
                                %>
                                <p>0</p>
                                <%
                                    }
                                } else {
                                %>
                                <p>0</p>
                                <%
                                    }
                                %>
                            </td>
                            <td>
                                <form action="MainController?action=createnewdish" method="post">
                                    <input type="hidden" value="<%= i.getIngredientID()%>" name="ingID">
                                    <input type="hidden" value="true" name="adding">
                                    <input type="number" name="quantity" min="0" required="">
                                    <input type="submit">
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
        </div>  
        <div style="height: 70px;"></div>
    </body>
</html>
