<%-- 
    Document   : Login
    Created on : Jun 3, 2024, 8:33:41 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/LoginStyle.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="Header.jsp" %>
    <di><img src="Picture/minimalism-minimalist-food-design.jpg" class="changeimg"/></di>
    <div class="login-container">
        <div class="login">
            <form action="MainController" method="post">
                <h1 class="changeh1">USER LOGIN</h1>
                <p class="pi">Your account</p>
                <p><img src="Picture/user.png" class="changeicon"/><input type="text" name="txtemail" placeholder=" Enter email" required="" /></p>
                <p class="pi">Password</p>
                <p><img src="Picture/unlock.png" class="changeicon"/><input type="password" name="txtpassword" placeholder=" Enter password" required="" /></p>
                <div class="flex-container">
                <p class="changeright"><%= (request.getAttribute("ERROR") != null)? request.getAttribute("ERROR") : "" %></p>
                <p class="changeleft"><a href="MainController?action=checkuserpage" class="changeleft">Forgot password?</a></p>
                </div>
                <button type="submit" name="action" value="login" class="button">Login</button>
                <!--<p><input type="submit" name="action"  value="login" /></p>-->
            </form>
        </div>
        </div>
    </body>
</html>
