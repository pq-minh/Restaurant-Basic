<%-- 
    Document   : AlbumToDish
    Created on : Jul 1, 2024, 8:49:43 AM
    Author     : nvhoa
--%>

<%@page import="mylib.Lib"%>
<%@page import="ultils.MyLib"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Photo Album</title>
        <link rel="stylesheet" href="Css/bootstrap.css">
        <link rel="stylesheet" href="Css/albumtodish.css">
    </head>
    <body>
        <%
            Lib l = new Lib();
            String a = "food1.jpg;food2.jpg;food3.jpg;food4.jpg;food5.jpg;food6.jpg;food7.jpg;food8.jpg;food9.jpg;food10.jpg;food11.jpg;piza.jpg;supageti.jpg;bundaumantom.jpg;bunriu.jpg;hutiu.jpg;mitom.jpg";
            String[] imgList = a.split(";");
        %>
        <div class="container">
            <h1 class="title">My Album</h1>
            <hr class="custom-hr">
            <div class="album row">
                <%
                    for (String i : imgList) {
                %>
                <div class="col-md-4" style="width: 350px; margin: 15px;">
                    <%
                        String update = (String) request.getAttribute("update");
                        boolean c = (l.checkString(update) && update.equalsIgnoreCase("true"));
                    %>            
                    
                    <form action="MainController?action=<%= c ? "updatedish" : "createnewdish"%>" method="post">
                        <input type="hidden" name="imgname" value="<%= i%>">
                        <button type="submit" style="background: none; border: none; padding: 0; margin: 0; cursor: pointer;">
                            <img src="Picture/<%= i%>">
                        </button>
                    </form>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>

