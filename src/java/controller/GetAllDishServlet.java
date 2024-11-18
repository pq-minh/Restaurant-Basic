/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DishDao;
import dto.Dish;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class GetAllDishServlet extends HttpServlet {

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
            String dishname = request.getParameter("txtdishname");
            String sort = request.getParameter("sort");
            String meal = request.getParameter("meal");
            String type = request.getParameter("type");
            String week = request.getParameter("week");
            DishDao d = new DishDao();

            ArrayList<Dish> list = new ArrayList<>();
            if (dishname == null || dishname.isEmpty()) {
                dishname = "";
                list = d.getDishs("");
            } else {
                list = d.getDishs(dishname.trim());
            }

            if (meal != null && !meal.isEmpty()) {
                switch (meal) {
                    case "1":
                        list = d.getDishMeal(1);
                        break;
                    case "2":
                        list = d.getDishMeal(2);
                        break;
                    case "3":
                        list = d.getDishMeal(3);
                        break;
//                    default:
//                        list = d.sortDishLetter(list);
//                        break;
                }
            } else {
                list = d.sortDishLetter(list);
            }

            if (week != null && !week.isEmpty()) {
                list = d.getDishForMenu();
            } else {
                list = d.sortDishLetter(list);
            }

            if (type != null && !type.isEmpty()) {
                int meale = 0;
                if (meal == null || meal.isEmpty()) {
                    meale = 0;
                } else {
                    meale = Integer.parseInt(meal.trim());
                }
                switch (type) {
                    case "savory":
                        list = d.getDishTypeMeal(7, meale, week, dishname);
                        break;
                    case "vegan":
                        list = d.getDishTypeMeal(2, meale,week, dishname);
                        break;
                }
            } else {
                list = d.sortDishLetter(list);
            }

            if (sort != null && !sort.isEmpty()) {
                switch (sort) {
                    case "1":
                        list = d.sortDishPriceAsc(list, false);
                        break;
                    case "2":
                        list = d.sortDishPriceAsc(list, true);
                        break;
//                    default:
//                        list = d.sortDishLetter(list);
//                        break;
                }
            } else {
                list = d.sortDishLetter(list);
            }

            request.setAttribute("ListDishs", list);
            request.getRequestDispatcher("MainController?action=" + IConstant.MENU_PAGE + "&dishname=" + dishname).forward(request, response);
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
