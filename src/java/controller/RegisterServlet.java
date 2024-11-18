/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDao;
import dto.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class RegisterServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8"); // Set request encoding to UTF-8
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String email = request.getParameter("txtemail");
            String password = request.getParameter("txtpassword");
            String repassword = request.getParameter("txtrepassword");
            String fullname = request.getParameter("txtfullname");
            String phonenumber = request.getParameter("txtphonenumber");
            //String dateofbirth = request.getParameter("txtdateofbirth");
            AccountDao d = new AccountDao();
            Account acc = d.getUser(email);

            //Date sqlDate = Date.valueOf(dateofbirth);
            if (email == null || password == null || repassword == null || fullname == null || phonenumber == null) {
                request.getRequestDispatcher("MainController?action=" + IConstant.REGISTER_PAGE).forward(request, response);
                return;
            }

            if (!password.equals(repassword)) {
                String ms = "Password error";
                request.setAttribute("error10", ms);
                request.getRequestDispatcher("MainController?action=" + IConstant.REGISTER_PAGE).forward(request, response);
                return;
            }

            if (!phonenumber.matches("^[0]\\d{9}$")) {
                String ms = "The phone number must be exactly 10 digits";
                request.setAttribute("error100", ms);
                request.getRequestDispatcher("MainController?action=" + IConstant.REGISTER_PAGE).forward(request, response);
                return;
            }

            if (acc != null) {
                String ms = "Already have an account using this email";
                request.setAttribute("error1000", ms);
                request.getRequestDispatcher("MainController?action=" + IConstant.REGISTER_PAGE).forward(request, response);
            } else {
                int rs = d.insertUser(email.trim(), fullname.trim(), password.trim(), phonenumber.trim());
                if (rs >= 1) {
                    String ms = "thankregister";
                    request.setAttribute("thankregister", ms);
                    request.getRequestDispatcher("MainController?action=" + IConstant.THANK).forward(request, response);
                } else {
                    String ms = "Sorry!, Pls try register in the next time";
                    request.setAttribute("error10000", ms);
                    request.getRequestDispatcher("MainController?action=" + IConstant.RECIPES_PAGE).forward(request, response);
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
