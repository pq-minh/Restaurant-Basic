<%-- 
    Document   : Register
    Created on : Jun 9, 2024, 5:04:08 PM
    Author     : Asus
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/registerStyle.css" rel="stylesheet">
        <link href="Css/bootstrap.css">

    </head>
    <body>
        <%@include file="Header.jsp" %>
        <!--<img src="Picture/cake.jpg" class="changeimg" />-->

        <div>
            <img src="Picture/w4.jpg" class="changeimg"/>
        </div>

        <div class="register-container">
            <div class="register">
                <form action="MainController" method="post" accept-charset="UTF-8">
                    <h1 class="changeh1">REGISTER</h1>
                    <p class="pi">Fullname</p>
                    <p><input type="text" name="txtfullname" placeholder=" Enter email" required="" /></p>
                    <p class="pi">Phone number</p>
                    <p><input type="text" name="txtphonenumber" placeholder="Your PhoneNumber" maxlength="10" minlength="10" required=""/></p>
                    <p class="pi">Email</p>
                    <p><input type="email" name="txtemail" placeholder=" Enter email" required="" /></p>
                    <p class="pi">Password</p>
                    <p><input type="password" name="txtpassword" placeholder=" Enter password" required="" /></p>
                    <p class="pi">Confirm password</p>
                    <p><input type="password" name="txtrepassword" placeholder=" Confirm password" required="" /></p>
                    <button type="submit" name="action" value="register" class="button">REGISTER</button>
                    <!--<p><input type="submit" name="action"  value="login" /></p>-->
                </form>
                <p class="changeright"><%= (request.getAttribute("error10") != null) ? request.getAttribute("error10") : ""%></p>
                <p class="changeright"><%= (request.getAttribute("error100") != null) ? request.getAttribute("error100") : ""%></p>
                <p class="changeright"><%= (request.getAttribute("error1000") != null) ? request.getAttribute("error1000") : ""%></p>
                <p class="changeright"><%= (request.getAttribute("error10000") != null) ? request.getAttribute("error10000") : ""%></p>
            </div>
        </div>


    </body>
</html>
