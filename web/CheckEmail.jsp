<%-- 
    Document   : CheckEmail
    Created on : Jun 14, 2024, 11:04:07 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/LoginStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
    <di><img src="Picture/minimalism-minimalist-food-design.jpg" class="changeimg"/></di>

    <div class="login-container">
        <div class="login">
            <form action="MainController" method="post">
                <h1 class="changeh1">CHECK USER </h1>
                <p class="pi">Your account</p>
                <p><img src="Picture/user.png" class="changeicon"/><input type="text" name="txtemail" placeholder=" Enter email" required="" /></p>
                <div class="flex-container">
                    <p class="changeright" style="color: #ffffff">a</p>
                    <p class="changeleft changeleft1"><%= (request.getAttribute("error") != null) ? request.getAttribute("error") : ""%></p>
                </div>
                <button type="submit" name="action" value="checkuser" class="button button1">CHECK</button>
                <!--<p><input type="submit" name="action"  value="login" /></p>-->
            </form>
        </div>
    </div>
</body>
</html>
