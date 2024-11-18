<%-- 
    Document   : AddDishToDB
    Created on : Jun 28, 2024, 6:58:19 AM
    Author     : nvhoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/addnewingredient.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />

        <div class="box">
            <h1>Add New  Ingredient</h1>
            <form action="MainController?action=addnewingredient" method="post">
                <p>Ingredient Name</p>
                <input type="text" name="ingname" required="" placeholder="Enter ingredient name"><br>
                <p>Quantity</p>
                <input type="number" name="ingquantity" step="1" min="0" placeholder="Enter quantity"><br>
                <p>Image</p>
                <input type="text" name="img" required="" placeholder="Enter image link"><br>
                <input type="hidden" value="true" name="adding">
                <input type="submit">
            </form>
            <%
                String ms1 = (String) request.getAttribute("FAIL");
                String ms2 = (String) request.getAttribute("SUCCESS");
                if (ms1 != null) {
            %>
            <h3><%= ms1%></h3>
            <%
            } else if (ms2 != null) {
            %>
            <h3><%= ms2 %></h3>
            <%
                }
            %>
        </div>
    </body>
</html>
