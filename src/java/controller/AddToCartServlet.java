/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DishDao;
import dto.Account;
import dto.Dish;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
public class AddToCartServlet extends HttpServlet {

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
            String dishid = request.getParameter("dishid");
            DishDao d = new DishDao();
            Dish dish = d.getDish(Integer.parseInt(dishid.trim()));

            HttpSession session = request.getSession();
//            Account acc = (Account) session.getAttribute("LoginedAcc");
//            if (acc == null) {
//                request.getRequestDispatcher("MainController?action=" + IConstant.LOGIN_PAGE).forward(request, response);
//                return;
//            }

            if (dish != null) {
//                HttpSession session = request.getSession();
                HashMap<Dish, Integer> cart = (HashMap<Dish, Integer>) session.getAttribute("cart");

                if (cart == null) {
                    cart = new HashMap<>();
                    cart.put(dish, 1);
                } else {
                    boolean find = false;
                    for (Dish tmp : cart.keySet()) {
                        if (tmp.getDishID() == Integer.parseInt(dishid.trim())) {
                            find = true;
                            int quantity = cart.get(tmp);
                            quantity++;
                            cart.put(tmp, quantity);
                        }
                    }
                    if (!find) {
                        cart.put(dish, 1);
                    }
                }

                session.setAttribute("cart", cart);
                session.setAttribute("lastAddedTime", new Date());
                request.getRequestDispatcher("MainController?action=" + IConstant.MENU).forward(request, response);
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
