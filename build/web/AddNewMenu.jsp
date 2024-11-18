<%-- 
    Document   : AddNewMenu
    Created on : Jun 23, 2024, 9:53:34 AM
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
        <link rel="stylesheet" href="Css/addnewmenu.css"/>
        <style>
            input{
                color: black;
            }
        </style>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div class="box">
            <h1>Create New Menu</h1>
            <form action="MainController?action=addnewmenu" method="post">
                <p>Menu Name</p>
                <input type="text" name="menuname" placeholder="Enter menu name" required style="color: black;"><br>
                <p>Start Date</p>
                <input type="text" name="menustartdate" placeholder="Start date" required style="color: black;"><br>
                <p>End Date</p>
                <input type="text" name="menuenddate" placeholder="End date" required style="color: black;"><br>
                <input type="submit" value="Create New Menu">
            </form>
            <div class="not">
                <%
                    String ms1 = (String) request.getAttribute("ERROR");
                    String ms2 = (String) request.getAttribute("SUCCEED");
                    if (ms1 != null) {
                %>
                <h3><%= ms1%></h3>
                <%
                } else if (ms2 != null) {
                %>
                <h3><%= ms2%></h3>
                <a style="text-decoration: underline; font-family: 'Times New Roman', Times, serif;" href="MainController?action=adddishtomenu">Add Dish To Menu</a>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
