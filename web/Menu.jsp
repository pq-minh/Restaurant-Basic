<%-- 
    Document   : Menu
    Created on : Jun 16, 2024, 8:39:03 PM
    Author     : Asus
--%>

<%@page import="dao.DishDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.Dish"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Css/menuStyle.css" rel="stylesheet"/>
        <link href="Css/bootstrap.css" rel="stylesheet"/>
        <link href="Css/headandfoot.css" rel="stylesheet"/>

    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div>
            <!--<a class="header-text">Menu</a>-->
            <img src="Picture/Spacekitchen1.jpg" class="img-header"/>
        </div>
        <div style="height: 50px">

        </div>
        <br/>

        <div class="row" >
            <div class="col-md-8" >
                <form class="search" action="MainController?action=menu" method="post">
                    <a  style="align-items: center;">
                        <input type="text" name="txtdishname" value="<%= request.getParameter("dishname") %>" class="searchbox"/>
                    </a>
                    <button type="submit" name="action" value="submit" class="button-search">
                        <img src="Picture/research.png" class="search-icon"/>
                    </button>
                </form>
            </div>

            <div class="col-md-2">
                <ul class="sort-menu">
                    <li class="right-item">
                        <a>Sort</a>
                        <ul class="sub-menu">
                            <%
                                String meal = request.getParameter("meal");
                                if (meal == null) {
                                    meal = "";
                                }
                                String type = request.getParameter("type");
                                if (type == null) {
                                    type = "";
                                }
                                String week = request.getParameter("week");
                                if (week == null) {
                                    week = "";
                                }
                                String dishname = request.getParameter("dishname");
                                if (dishname == null) {
                                    dishname = "";
                                }
                            %>
                            <li><a href="MainController?action=menu&sort=1&txtdishname=<%= dishname%>&meal=<%= meal%>&type=<%= type%>&week=<%= week%>">Price: High to low</a></li>
                            <li><a href="MainController?action=menu&sort=2&txtdishname=<%= dishname%>&meal=<%= meal%>&type=<%= type%>&week=<%= week%>">Price: Low to high</a></li>
                        </ul>
                    </li>
                </ul>
            </div>

            <div class="col-md-2">
                <ul class="sort-menu">
                    <li class="right-item">
                        <a>Type</a>
                        <ul class="sub-menu">
                            <li><a href="MainController?action=menu&type=savory&txtdishname=<%= dishname%>&meal=<%= meal%>&week=<%= week%>">Savory</a></li>
                            <li><a href="MainController?action=menu&type=vegan&txtdishname=<%= dishname%>&meal=<%= meal%>&week=<%= week%>">Vegan</a></li>
                        </ul>
                    </li>
                </ul>
            </div>

        </div>

        <div class="row" >
            <div class="col-md-3">
                <table class="table1">
                    <tr>
                        <td><a href="MainController?action=menu">Categories</a></td>
                    </tr>
                    <tr>
                        <td><a href="MainController?action=menu&meal=1">Breakfast</a></td>
                    </tr>
                    <tr>
                        <td><a href="MainController?action=menu&meal=2">Lunch</a></td>
                    </tr>
                    <tr>
                        <td><a href="MainController?action=menu&meal=3">Dinner</a></td>
                    </tr>
                    <tr>
                        <td><a href="MainController?action=menu&week=now">Week's menu</a></td>
                    </tr>
                </table>

            </div>

            <div class="col-md-9" >
                <div class="dish-container">
                    <%            ArrayList<Dish> list = (ArrayList) request.getAttribute("ListDishs");
                        if (list != null) {
                    %>
                    <% for (Dish di : list) {%>
                    <div class="dish-item">
                        <a href="MainController?action=recipes&dishid=<%= di.getDishID()%>"><img src="<%= di.getImage()%>" class="img-food"/></a>
                        <h3 style="font-size: 22px"><%= di.getDishName()%></h3>
                        <p>Price : <%= String.format("%.3f", di.getDishPrice())%> VND</p>

                        <form action="MainController" method="post" style="display: inline-block">
                            <input type="hidden" name="action" value="cart">
                            <input type="hidden" name="dishid" value="<%= di.getDishID()%>">
                            <button type="submit" class="button-cart">Add to cart</button>
                        </form>

                        <button class="button-cart1" onclick="openModal('<%= di.getDishID()%>')">Add to plan</button>

                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p>Your menu is empty</p>
                    <%
                        }
                    %>    
                </div>
            </div>
        </div>

        <!-- The Modal -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <form id="planForm" action="MainController" method="post">
                    <input type="hidden" name="action" value="plan">
                    <input type="hidden" name="dishid" id="modalDishId">
                    <label for="day">Choose a day:</label>
                    <select name="day" id="day">
                        <option value="Monday">Monday</option>
                        <option value="Tuesday">Tuesday</option>
                        <option value="Wednesday">Wednesday</option>
                        <option value="Thursday">Thursday</option>
                        <option value="Friday">Friday</option>
                        <option value="Saturday">Saturday</option>
                        <option value="Sunday">Sunday</option>
                    </select>
                    <button type="submit" class="button-modal">Add</button>
                    <button type="button" class="button-modal" id="cancelButton">Cancel</button>
                </form>
            </div>
        </div>

        <script>
            // Get the modal
            var modal = document.getElementById("myModal");

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // Get the cancel button
            var cancelButton = document.getElementById("cancelButton");

            // Function to open the modal
            function openModal(dishid) {
                document.getElementById("modalDishId").value = dishid;
                modal.style.display = "block";
            }

            // When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
            }

            // When the user clicks on cancel button, close the modal
            cancelButton.onclick = function () {
                modal.style.display = "none";
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }

            document.addEventListener("DOMContentLoaded", function () {
                var error1Message = '<%= request.getAttribute("error1")%>';
                var error2Message = '<%= request.getAttribute("error2")%>';
                if (error1Message !== 'null') {
                    alert(error1Message);
                }
                if (error2Message !== 'null') {
                    alert(error2Message);
                }
            });

        </script>
    </body>
</html>
