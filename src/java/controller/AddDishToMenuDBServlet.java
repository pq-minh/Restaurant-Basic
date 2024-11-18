/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dao.MenuDao;
import dto.Dish;
import dto.Menu;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class AddDishToMenuDBServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            MenuDao md = new MenuDao();
            DishDao dd = new DishDao();
            String menuID = (String) session.getAttribute("menuID");
            String dishID = request.getParameter("id");
            List<Dish> dList = dd.getAllDishInMenu(Integer.parseInt(menuID));
            Menu menu = md.getMenuByMenuID(Integer.parseInt(menuID));
            

            if (menuID != null) {
                if (dishID != null) {

                    List<Integer> dishIDList = new ArrayList<>();

                    for (Dish dish : dList) {
                        dishIDList.add(dish.getDishID());
                    }

                    if (!dishIDList.contains(Integer.parseInt(dishID))) {
                        dd.addDishToMenu(Integer.parseInt(menuID), Integer.parseInt(dishID));
                    }
                } 
            }
            dList = dd.getAllDishInMenu(Integer.parseInt(menuID));
            request.setAttribute("dishList", dList);
            request.setAttribute("menuInf", menu);
            request.getRequestDispatcher("UpdateMenu.jsp").forward(request, response);
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
