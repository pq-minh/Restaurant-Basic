<%-- 
    Document   : HeaderStaff
    Created on : Jul 9, 2024, 8:46:54 PM
    Author     : nvhoa
--%>

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
            navbar-nav li a{
                font-size: 15px;
                text-align: center;
            }
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
                    %>
                    <li><a href="MainController?action=home">Home</a></li> 
                    <li><a href="MainController?action=manageorder">Manage Order</a></li> 
                    <li><a href="MainController?action=addnewmenu">Create Menu</a></li>
                    <li><a href="MainController?action=showallmenu">Update Menu</a></li>
                    <li><a href="MainController?action=createnewdish">Create Dish</a></li>
                    <li><a href="MainController?action=updatedish">Update Dish</a></li>
                    <li><a href="MainController?action=addnewingredient">Add Ingredient</a></li>
                    <li><a href="MainController?action=updateingredient">Update Ingredient</a></li>
                    <li><a href="MainController?action=manageaccount">Manage Account</a></li>
                    <li class="account-menu">
                        <a><%= acc.getFullName()%></a>
                        <ul class="sub-menu">
                            <li><a href="MainController?action=customerprofile">Profile</a></li>
                            <li><a href="MainController?action=logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>

    </body>
</html>
