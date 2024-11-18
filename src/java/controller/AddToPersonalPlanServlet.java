/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DishDao;
import dao.PlanDao;
import dto.Account;
import dto.Dish;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Asus
 */
public class AddToPersonalPlanServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            int dishid = Integer.parseInt(request.getParameter("dishid"));
            String day = request.getParameter("day");
            DishDao d = new DishDao();
            PlanDao pd = new PlanDao();
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("LoginedAcc");

            if (acc == null) {
                request.getRequestDispatcher("MainController?action=" + IConstant.LOGIN_PAGE).forward(request, response);
                return;
            }

            if (dishid == 0 || day == null) {
                request.getRequestDispatcher("MainController?action=" + IConstant.HOME).forward(request, response);
                return;
            }

            int accformplan = pd.getAccFromPlan(acc.getAccountID());
            if (accformplan != acc.getAccountID()) {

                int rs = pd.insertToPlan(acc.getAccountID());
                
                if (rs >= 1) {
                    int perid = pd.getPersIDFromPlan(acc.getAccountID());
                        int rs1 = pd.insertToMakePlan(dishid, day, perid);
                        
                        if (rs1 >= 1) {
                            request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                        } else {
                            request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                        }
                    
                } else {
                    String msg = "Sorry! Please try again in this next time";
                    request.setAttribute("error1", msg);
                    request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                }

            } else {
                int perid = pd.getPersIDFromPlan(acc.getAccountID());
                int rss3 = pd.checkDishFromPlan(dishid, day, dishid);
                
                if (rss3 == 0) {
                    int rs5 = pd.editDishFormPlan(dishid, day, perid, 1);
                    
                    if (rs5 >= 1) {
                        request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                    } else {
                        request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                    }
                } else {
                    int rs1 = pd.insertToMakePlan(dishid, day, perid);
                    
                    if (rs1 >= 1) {
                        request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                    } else {
                        String msg = "This item is already included in your personal plan";
                        request.setAttribute("error2", msg);
                        request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
                    }
                }
            }

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
