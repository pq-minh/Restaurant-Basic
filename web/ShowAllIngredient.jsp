<%-- 
    Document   : ShowIngredientToJustify
    Created on : Jun 28, 2024, 7:50:44 AM
    Author     : nvhoa
--%>

<%@page import="dto.Ingredient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/showalling.css"/>
    </head>
    <style>
        table tbody p{
            font-size: 18px;
        }
    </style>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div style="height: 70px;"></div>
        <%
            List<Ingredient> aiL = (List<Ingredient>) request.getAttribute("aiList");
            String ms = (String) request.getAttribute("FAIL");
        %>
        <h1 style="text-align: center; font-family: initial; font-weight: bold">All Ingredients In Stock</h1>
        <table style="width: 85%; margin: 0px auto;">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Quantity In Stock</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <%
                if (aiL != null) {
                    int c = 0;
                    for (Ingredient i : aiL) {
                        c++;
            %>
            <tbody>
                <tr>
                    <td>
                        <p><%= c%></p> 
                    </td>
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
                        <form action="MainController?action=updateingredient" method="post">
                            <input type="hidden" value="<%= i.getIngredientID()%>" name="ingID">
                            <input type="submit" value="Delete">
                            <input type="hidden" value="true" name="delete">
                        </form>
                    </td>
                    <td>
                        <form action="MainController?action=updateingredient" method="post">
                            <input type="hidden" value="<%= i.getIngredientID()%>" name="ingID">
                            <input type="submit" value="Update">
                            <input type="hidden" value="true" name="update">
                        </form>
                    </td>
                </tr>
            </tbody>
            <%
                    }
                }
            %>
        </table>
        <div style="height: 70px;"></div>
        <script type="text/javascript">
            <%
                if (ms != null) {
            %>
            alert("<%= ms%>");
            <%
                }
            %>
        </script>
    </body>
</html>
