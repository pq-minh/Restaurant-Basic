/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDao;
import dao.OrderDao;
import dto.Account;
import dto.Dish;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Asus
 */
public class SaveOrderServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("LoginedAcc");

            if (acc == null) {
                String msg = "Oop! Sorry, you need login before buy dish!";
                request.setAttribute("Error", msg);
                request.getRequestDispatcher("MainController?action=" + IConstant.LOGIN_PAGE).forward(request, response);
                return;
            }

            AccountDao d = new AccountDao();
            Account acc1 = d.getUser(acc.getEmail());
            if (acc1.getAddress() == null || acc1.getCity() == null || acc1.getDistrict() == null) {
                String msg = "Oop! Sorry, your address like emply, pls update profile before buy";
                request.setAttribute("Error2", msg);
                request.getRequestDispatcher("MainController?action=" + IConstant.CART_PAGE).forward(request, response);
            } else {
                int accid = acc.getAccountID();
                HashMap<Dish, Integer> cart = (HashMap<Dish, Integer>) session.getAttribute("cart");
                OrderDao od = new OrderDao();
                int result = od.saveOrder(accid, cart);
                if (result >= 1) {
                    session.removeAttribute("cart");
                    String msg = "thankforbuy";
                    request.setAttribute("msgfromsave", msg);
                    request.getRequestDispatcher("MainController?action=" + IConstant.THANK).forward(request, response);
                } else {
                    String msg = "Can't not buy. Please try again in next time";
                    request.setAttribute("Error1", msg);
                    request.getRequestDispatcher("MainController?action=" + IConstant.CART_PAGE).forward(request, response);
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
