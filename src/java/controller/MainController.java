/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String choice = request.getParameter("action");
            String url = "";

            if (choice != null) {
                switch (choice) {
                    case IConstant.HOME:
                        url = "Home.jsp";
                        break;
                    case IConstant.LOGIN:
                        url = "LoginServlet";
                        break;
                    case IConstant.ADMINHOME:
                        url = "AdminHome.jsp";
                        break;
                    case IConstant.LOGIN_PAGE:
                        url = "Login.jsp";
                        break;
                    case IConstant.REGISTER_PAGE:
                        url = "Register.jsp";
                        break;
                    case IConstant.REGISTER:
                        url = "RegisterServlet";
                        break;
                    case IConstant.LOGOUT:
                        url = "LogOutServlet";
                        break;
                    case IConstant.RESETPASSWORD:
                        url = "UpdateNewPasswordServlet";
                        break;
                    case IConstant.RESETPASSWORD_PAGE:
                        url = "ForgotPassword.jsp";
                        break;
                    case IConstant.CHECKUSER_PAGE:
                        url = "CheckEmail.jsp";
                        break;
                    case IConstant.CHECKUSER:
                        url = "CheckEmailServlet";
                        break;
                    case IConstant.MENU:
                        url = "GetAllDishServlet";
                        break;
                    case IConstant.MENU_PAGE:
                        url = "Menu.jsp";
                        break;
                    case IConstant.PERSONALPLAN_PAGE:
                        url = "PersonalPlan.jsp";
                        break;
                    case IConstant.PERSONALPLAN:
                        url = "AddToPersonalPlanServlet";
                        break;
                    case IConstant.SHOWPERSONALPLAN:
                        url = "GetPlanServlet";
                        break;
                    case IConstant.EDITPERSONALPLAN:
                        url = "EditPlanServlet";
                        break;
                    case IConstant.ORDERHISTORY_PAGE:
                        url = "ViewOrderHistory.jsp";
                        break;
                    case IConstant.ORDERHISTORY:
                        url = "GetOrderDetail";
                        break;
                    case IConstant.CART:
                        url = "AddToCartServlet";
                        break;
                    case IConstant.CART_PAGE:
                        url = "ViewCart.jsp";
                        break;
                    case IConstant.BUY:
                        url = "SaveOrderServlet";
                        break;
                    case IConstant.THANK:
                        url = "ThankYou.jsp";
                        break;
                    case IConstant.RECIPES:
                        url = "RecipesServlet";
                        break;
                    case IConstant.RECIPES_PAGE:
                        url = "ViewRecipes.jsp";
                        break;
                    case IConstant.UPDATEPROFIE:
                        url = "UpdateProfileServlet";
                        break;
                    case IConstant.UPDATEPROFIE_PAGE:
                        url = "UpdateProfileCustomer.jsp";
                        break;
                    case IConstant.EDITCART:
                        url = "EditCartServlet";
                        break;
                    case IConstant.MANAGEORDER:
                        url = "ManageOrderServlet";
                        break;
                    case IConstant.DELIVERY:
                        url = "DeliveryServlet";
                        break;
                    case IConstant.CANCELORDER:
                        url = "CancelOrderServlet";
                        break;
                    case IConstant.ADDNEWMENU:
                        url = "AddNewMenuServlet";
                        break;
                    case IConstant.SEARCHDISH:
                        url = "SearchDishServlet";
                        break;
                    case IConstant.ADDDISHTOMENU:
                        url = "AddDishToMenuServlet";
                        break;
                    case IConstant.CREATENEWMENU:
                        url = "CreateNewMenuServlet";
                        break;
                    case IConstant.SHOWALLMENU:
                        url = "ShowAllMenuServlet";
                        break;
                    case IConstant.UPDATEMENU:
                        url = "UpdateMenuServlet";
                        break;
                    case IConstant.ADDDISHTOMENUDB:
                        url = "AddDishToMenuDBServlet";
                        break;
                    case IConstant.CREATENEWDISH:
                        url = "CreateNewDishServlet";
                        break;
                    case IConstant.UPDATEDISH:
                        url = "UpdateDishServlet";
                        break;
                    case IConstant.ADDNEWING:
                        url = "AddNewIngredientServlet";
                        break;
                    case IConstant.UPDATEING:
                        url = "UpdateIngredientServlet";
                        break;
                    case IConstant.MANAGEACCOUNT:
                        url = "ManageAccountServlet";
                        break;
                    case IConstant.ALBUMDISH:
                        url = "AlbumDishServlet";
                        break;
                }
            } else {
                url = "Home.jsp";
            }

            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
