<%-- 
    Document   : Header
    Created on : Jun 5, 2024, 10:58:08 AM
    Author     : Asus
--%>

<%@page import="dao.AccountDao"%>
<%@page import="dto.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/headandfoot.css" rel="stylesheet"/>
        <link href="Css/bootstrap.css" rel="stylesheet"/>
        <script src="Js/scroll.js"></script>
        <style>
            *{
                margin: 0px;
                padding: 0px;
            }
        </style>
    </head>
    <body>
        <div class="headerr collapse navbar-collapse">
            <div class="flex-row">
                <img class="logo" id="logo" src="Picture/Logo1.2.png" />
                <ul class="nav navbar-nav">
                    <%
                        Account acc = (Account) session.getAttribute("LoginedAcc");
                        if (acc != null) {
                            if (acc.getRole().equalsIgnoreCase("Staff") || acc.getRole().equalsIgnoreCase("Admin")) {
                    %>
                    <li><a href="MainController?action=manageorder">Staff</a></li> 
                        <%
                                }
                            }
                        %>
                    <li><a href="MainController?action=home">Home</a></li> 
                    <li><a href="MainController?action=menu">Menu</a></li>

                    <%
                        if (acc == null) {
                    %>
                    <li><a href="MainController?action=loginpage">Login</a></li>
                    <li><a href="MainController?action=registerpage">Register</a></li>

                    <%
                    } else {
                        AccountDao d = new AccountDao();
                        Account accdb = d.getUser(acc.getEmail());
                    %>
                    <li class="account-menu">
                        <a><%= accdb.getFullName()%></a>
                        <ul class="sub-menu">
                            <li><a href="MainController?action=getplan">Personal Plan</a></li>
                            <li><a href="MainController?action=updateprofilecustomerpage">Update profile</a></li>
                            <li><a href="MainController?action=orderhistory">Purchase history</a></li>
                            <li><a href="MainController?action=logout">Logout</a></li>
                        </ul>
                    </li>
                    <%
                        }
                    %>
                    <li><a href="MainController?action=cartpage"><img src="Picture/shopping-cart1.1.png" class="cart" id="cart"/></a></li>
                </ul>
            </div>
        </div>

    </body>
</html>
