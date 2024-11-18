<%-- 
    Document   : ForgotPassword
    Created on : Jun 14, 2024, 10:50:28 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/forgotpasswordStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
    <di><img src="Picture/minimalism-minimalist-food-design.jpg" class="changeimg"/></di>
    <div class="login-container">
        <div class="login">
            <form action="MainController" method="post">
                <h1 class="changeh1">UPDATE PASSWORD</h1>
                <p class="pi">Password</p>
                <p><img src="Picture/unlock.png" class="changeicon"/><input type="password" name="txtnewpassword" placeholder=" Enter new password" required="" /></p>
                <p class="pi">Confirm password</p>
                <p><input type="password" name="txtrepassword" placeholder=" Confirm password" required="" /></p>
                <div class="flex-container">
                    <p class="changeleft"><%= (request.getAttribute("ERROR") != null) ? request.getAttribute("ERROR") : ""%></p>
                    <p class="changeleft" style="color: #ffffff">a</p>
                </div>
                <p><input type="hidden" name="txtemail" value="<%= request.getAttribute("email")%>"/></p>
                <button type="submit" name="action" value="forgetpassword" class="button">UPDATE</button>
                <!--<p><input type="submit" name="action"  value="login" /></p>-->
            </form>
        </div>
    </div>
</body>
</html>
