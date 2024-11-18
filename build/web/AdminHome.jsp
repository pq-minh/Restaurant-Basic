<%-- 
    Document   : AdminHome
    Created on : Jun 3, 2024, 8:44:57 PM
    Author     : Asus
--%>

<%@page import="dto.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/home.css" rel="stylesheet" />
    </head>
    <body>
        <%  Account accc = (Account) session.getAttribute("LoginedAcc");
            if (accc == null) {
                request.getRequestDispatcher("MainController?action=home").forward(request, response);
                return;
            }
        %>
        <%@include file="Header.jsp" %>
        <img src="Picture/1169577.jpg" class="headerimg"/>
        <p>Dashboard</p>
        <p><a href="#">Manage Item</a></p>
    </body>
</html>
