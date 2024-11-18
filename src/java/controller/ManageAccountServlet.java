/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDao;
import dto.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nvhoa
 */
public class ManageAccountServlet extends HttpServlet {

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
            // Lay email , matkhau của tài khoản trong session  rồi kiểm tra bằng hàm getRole, 

            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("LoginedAcc");
            if (acc != null) {
                AccountDao ad = new AccountDao();
                String role = acc.getRole();

                if (role != null && role.equalsIgnoreCase("Admin")) {

                    String id = request.getParameter("id");
//====================== disable / activate account

                    String cs = request.getParameter("changestatus");
                    if (cs != null && cs.equalsIgnoreCase("activate") && id != null) {
                        ad.updateStatusAccount(Integer.parseInt(id), 1);
                    } else if (cs != null && cs.equalsIgnoreCase("disable") && id != null) {
                        ad.updateStatusAccount(Integer.parseInt(id), 0);
                    }

//====================== chnage role account
                    String crole = request.getParameter("crole");
                    String p = request.getParameter("promote");
                    String d = request.getParameter("demote");
                    if (p != null && p.equalsIgnoreCase("true") && id != null && crole != null) {
                        if (crole.equalsIgnoreCase("Customer")) {
                            ad.changeAccountRole(Integer.parseInt(id), "Staff");
                        }
                    }
                    if (d != null && d.equalsIgnoreCase("true") && id != null && crole != null) {
                        if (crole.equalsIgnoreCase("Staff")) {
                            ad.changeAccountRole(Integer.parseInt(id), "Customer");
                        }
                    }
//======================

                    List<Account> acL = ad.getAllAccounts();
                    request.setAttribute("acList", acL);
                    request.getRequestDispatcher("ManageAccount.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("Home.jsp").forward(request, response);
                }
            }else{
                request.getRequestDispatcher("Home.jsp").forward(request, response);
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
