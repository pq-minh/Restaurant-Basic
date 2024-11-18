<%-- 
    Document   : UpdateProfileCustomer
    Created on : Jul 8, 2024, 10:02:02 PM
    Author     : Asus
--%>

<%@page import="dao.AccountDao"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Profile</title>
        <link href="Css/profileStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <h1 class="h1edit">Update Profile</h1>
        <div class="container">
            <%            
                Account acc4 = (Account) session.getAttribute("LoginedAcc");
                if (acc4 != null) {
                    AccountDao d = new AccountDao();
                    Account account = d.getUser(acc4.getEmail());
                    String address = account.getAddress() != null ? account.getAddress(): "";
                    String district = account.getDistrict()!= null ? account.getDistrict(): "";
                    String city = account.getCity()!= null ? account.getCity(): "";
            %>
            <form action="MainController" method="post" >
                <h2>Fullname</h2>
                <p><input type="text" name="txtname" value="<%= account.getFullName()%>" required=""/></p>
                <h2>Phone number</h2>
                <p><input type="text" name="txtphonenumber" value="<%= account.getPhoneNumber() %>" placeholder="" maxlength="10" minlength="10" required=""/></p>
                <h2>Gender</h2>
                <a>Male<input type="radio" name="gender" value="Male" checked=""/></a>
                <a>Female<input type="radio" name="gender" value="Female"/></a>
                <h2>Address</h2>
                <p><input type="text" name="txtaddress" value="<%= address %>" placeholder="Số nhà, Tên đường, Tên phường" required=""/></p>
                
                  <select name="txtdistrict">
                    <option value="1" <%= "1".equals(district) ? "selected" : "" %>>Quận 1</option>
                    <option value="2" <%= "2".equals(district) ? "selected" : "" %>>Quận 2</option>
                    <option value="3" <%= "3".equals(district) ? "selected" : "" %>>Quận 3</option>
                    <option value="4" <%= "4".equals(district) ? "selected" : "" %>>Quận 4</option>
                    <option value="5" <%= "5".equals(district) ? "selected" : "" %>>Quận 5</option>
                    <option value="6" <%= "6".equals(district) ? "selected" : "" %>>Quận 6</option>
                    <option value="7" <%= "7".equals(district) ? "selected" : "" %>>Quận 7</option>
                    <option value="8" <%= "8".equals(district) ? "selected" : "" %>>Quận 8</option>
                    <option value="9" <%= "9".equals(district) ? "selected" : "" %>>Quận 9</option>
                    <option value="10" <%= "10".equals(district) ? "selected" : "" %>>Quận 10</option>
                    <option value="11" <%= "11".equals(district) ? "selected" : "" %>>Quận 11</option>
                    <option value="12" <%= "12".equals(district) ? "selected" : "" %>>Quận 12</option>
                </select>
                
                <select name="txtcity">
                    <option value="Hồ Chí Minh" <%= "Hồ Chí Minh".equals(city) ? "selected" : "" %>>Hồ Chí Minh</option>
                    <option value="Hà Nội" <%= "Hà Nội".equals(city) ? "selected" : "" %>>Hà Nội</option>
                    <option value="Biên Hòa" <%= "Biên Hòa".equals(city) ? "selected" : "" %>>Biên Hòa</option>
                </select>
                <br/>
                
                <input name="txtaccid" type="hidden" value="<%= acc4.getAccountID() %>"/>

                <button type="submit" name="action" value="updateprofilecustomer">Update</button>
            </form>
            <% } else {
                    request.getRequestDispatcher("MainController?action=home").forward(request, response);
                }%>
        </div>
    </body>
</html>
