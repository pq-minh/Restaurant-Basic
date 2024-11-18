<%-- 
    Document   : JustifyIngredient
    Created on : Jun 28, 2024, 7:40:36 AM
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
        <link rel="stylesheet" href="Css/updateing.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <%
            IngredientDao id = new IngredientDao();
            String ingIDSession = (String) session.getAttribute("ingID");
            Ingredient ing = id.getIngredientByID(Integer.parseInt(ingIDSession));
        %>
        <div class="row box">
            <div class="cont1 col-md-12">
                <h1>Update Ingredient</h1>
            </div>
            <div class="col-md-6 cont2">
                <h3 style="margin-top: 20px">Ingredient ID: <%= ing.getIngredientID()%></h3>
                <h3>Name: <%= ing.getIngredientName()%></h3>
                <h3>Quantity In Stock: <%= ing.getQuantity()%></h3>
            </div>

            <div class="col-md-6 cont3">
                <form action="MainController?action=updateingredient" method="post">
                    <label for="ingname">Name:</label>
                    <input type="text" name="ingname" id="ingname" placeholder="Enter ingredient name"><br>
                    <label for="inc">Increase:</label>
                    <input type="number" name="inc" id="inc" step="1" min="0" placeholder="Enter quantity"><br>
                    <label for="red">Reduce:</label>
                    <input type="number" name="red" id="red" step="1" min="0" placeholder="Enter quantity"><br>
                    <input type="hidden" value="<%= ing.getIngredientID()%>" name="ingID">
                    <input type="hidden" name="change" value="true">
                    <input type="submit" style="padding: 3px 10px;">
                </form>
            </div>
        </div>
        <div style="height: 70px;"></div>   
    </body>
</html>
