<%-- 
    Document   : ShowMenuToJustify
    Created on : Jul 2, 2024, 2:31:39 PM
    Author     : nvhoa
--%>

<%@page import="dto.Menu"%>
<%@page import="java.util.List"%>
<%@page import="dao.DishDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/bootstrap.css"/>
        <link rel="stylesheet" href="Css/showallmenu.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div>
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <div class="container">
            <div style="height: 70px;"></div>
            <div class="row">
                <%
                    DishDao dd = new DishDao();
                    List<Menu> mL = (List<Menu>) request.getAttribute("menuList");

                    for (Menu m : mL) {
                %>
                <div class="col-md-4">
                    <div class="menu-item">
                        <h3><%= m.getMenuName()%></h3>
                        <h3><%= m.getMenuStartDate()%></h3>
                        <h3><%= m.getMenuEndDate()%></h3>
                        <form action="MainController?action=updatemenu" method="post">
                            <input type="hidden" value="<%= m.getMenuID()%>" name="menuID">
                            <input type="submit" value="Update menu">
                        </form>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
