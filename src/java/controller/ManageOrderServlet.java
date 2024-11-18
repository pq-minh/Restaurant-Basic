/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDao;
import dao.OrderDao;
import dto.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mylib.Lib;

/**
 *
 * @author nvhoa
 */
public class ManageOrderServlet extends HttpServlet {

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
            AccountDao ad = new AccountDao();
            OrderDao od = new OrderDao();
            Lib l = new Lib();
            HttpSession session = request.getSession();

            String sk = request.getParameter("searchKey");
            String button = request.getParameter("button");

            List<Account> listA = ad.getAllCustomerAccountHaveOrder();

            if (button != null && button.equalsIgnoreCase("Search")) {
                if (sk != null && !sk.trim().isEmpty()) {
                    sk = sk.trim();
                    if (l.isEmail(sk)) {
                        listA = od.searchOrderByEmail(sk);
                    } else if (l.isPhoneNumber(sk)) {
                        listA = od.searchOrderByPhoneNumber(sk);
                    }
                    session.setAttribute("searchOrderKey", sk);
                }

            } else if (button != null && button.equalsIgnoreCase("Show All")) {
                session.removeAttribute("dateToSearch");
                session.removeAttribute("searchOrderKey");
            }

//=================
            String sok = (String) session.getAttribute("searchOrderKey");
            if (sok != null && !sok.isEmpty()) {
                if (l.isEmail(sok)) {
                    listA = od.searchOrderByEmail(sok);
                } else if (l.isPhoneNumber(sok)) {
                    listA = od.searchOrderByPhoneNumber(sok);
                }
            }
            
            String dtsSession = (String) session.getAttribute("dateToSearch");
            if (dtsSession != null && !dtsSession.isEmpty()) {
                String district = (String) session.getAttribute("district");
                if (district != null) {
                    request.getRequestDispatcher("ManageOrder.jsp").forward(request, response);
                }  
            }    

// =========search by date
            String dts = request.getParameter("dateToSearch");
            if (dts != null && !dts.isEmpty()) {
                String district = request.getParameter("district");
                if (district != null) {
                    session.setAttribute("district", district);
                    session.setAttribute("dateToSearch", dts);
                }
                request.getRequestDispatcher("ManageOrder.jsp").forward(request, response);
            }
//================

            if (listA.isEmpty()) {
                request.setAttribute("NOTFOUND", "No orders found!");
            } else {
                session.setAttribute("accountList", listA);
            }

            request.getRequestDispatcher("ManageOrder.jsp").forward(request, response);

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
