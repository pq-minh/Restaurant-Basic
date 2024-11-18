<%-- 
    Document   : ManageAccount
    Created on : Jun 28, 2024, 1:04:35 PM
    Author     : nvhoa
--%>

<%@page import="dto.Account"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/manageaccount.css"/>
    </head>
    <body>
        <jsp:include page="HeaderStaff.jsp" />
        <div style="height: 70px;"></div>
        <h1 style="text-align: center; font-family: initial; font-weight: bold">Manage Account</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Full Name</th>
                    <th>Gender</th>
                    <th>Date Of Birth</th>
                    <th>District</th>
                    <th>Address</th>
                    <th>Phone Number</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <%                
                Account acc = (Account) session.getAttribute("LoginedAcc");
                List<Account> acL = (List<Account>) request.getAttribute("acList");
                if (acL != null) {
                    for (Account c : acL) {
            %>
            <tbody>
                <tr>
                    <td>
                        <p><%= c.getAccountID() %></p> 
                    </td>
                    <td>
                        <p><%= c.getEmail()%></p> 
                    </td>
                    <td>
                        <p><%= c.getFullName() %></p> 
                    </td>
                    <td>
                        <p><%= c.getGender()%></p> 
                    </td>
                    <td>
                        <p><%= c.getDateOfBirth()%></p> 
                    </td>
                    <td>
                        <p><%= c.getDistrict()%></p> 
                    </td>
                    <td>
                        <p><%= 123%></p> 
                    </td>
                    <td>
                        <p><%= c.getPhoneNumber()%></p> 
                    </td>
                    <td>
                        <p><%= c.getRole()%></p> 
                    </td>
                    <td>
                        <%
                            if (c.getStatus() == 0) {
                        %>
                        <p>Disabled</p> 
                        <%
                        } else {
                        %>
                        <p>Activated</p> 
                        <%
                            }
                        %>
                    </td>
                    <td>
                        <%
                            if (!c.getRole().equalsIgnoreCase("Admin")) {
                                if (c.getStatus() == 0) {
                        %>
                        <form action = "MainController?action=manageaccount" method = "post" onsubmit="return confirmSubmission1();"> 
                            <input type="hidden" value="<%= c.getAccountID() %>" name="id">
                            <input type="submit" value="Activate">
                            <input type="hidden" value="activate" name="changestatus">
                        </form>
                        <%
                        } else {
                        %>
                        <form action = "MainController?action=manageaccount" method = "post" onsubmit="return confirmSubmission2();"> 
                            <input type="hidden" value="<%= c.getAccountID() %>" name="id">
                            <input type="submit" value="Disable">
                            <input type="hidden" value="disable" name="changestatus">
                        </form>
                        <%
                                }
                            }
                        %>
                    </td>
                    <td>
                        <%
                            if (!c.getRole().equalsIgnoreCase("Admin")) {
                        %>
                        <form action = "MainController?action=manageaccount" method = "post" onsubmit="return confirmSubmission3();"> 
                            <input type="hidden" value="<%= c.getAccountID() %>" name="id">
                            <input type="hidden" value="<%= c.getRole()  %>" name="crole">
                            <input type="submit" value="Promote">
                            <input type="hidden" value="true" name="promote">
                        </form>
                        <%
                            }
                        %>
                    </td>
                     <td>
                        <%
                            if (!c.getRole().equalsIgnoreCase("Admin")) {
                        %>
                        <form action = "MainController?action=manageaccount" method = "post" onsubmit="return confirmSubmission3();"> 
                            <input type="hidden" value="<%= c.getAccountID() %>" name="id">
                            <input type="hidden" value="<%= c.getRole()  %>" name="crole">
                            <input type="submit" value="Demote">
                            <input type="hidden" value="true" name="demote">
                        </form>
                        <%
                            }
                        %>
                    </td>
                </tr>
            </tbody>
            <%
                    }
                }
            %>
        </table>
        <div style="height: 70px;"></div>
        <script type="text/javascript">
            function confirmSubmission1() {
                return confirm("Are you sure to activate this account?");
            }
            function confirmSubmission2() {
                return confirm("Are you sure to disable this account?");
            }
            function confirmSubmission3() {
                return confirm("Are you sure to change role of this account?");
            }
        </script>
    </body>
</html>
