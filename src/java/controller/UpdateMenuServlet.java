/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dao.MenuDao;
import dao.OrderDao;
import dto.Dish;
import dto.Menu;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UpdateMenuServlet extends HttpServlet {

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
            DishDao dd = new DishDao();
            Lib l = new Lib();
            String id = request.getParameter("menuID");
            String rd = request.getParameter("removedish");
            String cmi = request.getParameter("changemenuinf");
            HttpSession session = request.getSession();
            
            MenuDao md = new MenuDao();
            String menuID = (String) session.getAttribute("menuID");
            
            if (id != null) {
                session.setAttribute("menuID", id);
                Menu menu = md.getMenuByMenuID(Integer.parseInt(id));
                List<Dish> dishL = dd.getAllDishInMenu(Integer.parseInt(id));
                request.setAttribute("menuInf", menu);
                request.setAttribute("dishList", dishL);
                request.getRequestDispatcher("UpdateMenu.jsp").forward(request, response);
            }

            if (menuID != null) {
                if (rd != null && rd.equalsIgnoreCase("true")) {        // remove dish
                    String dishID = request.getParameter("dishID");
                    dd.removeDishFromMenu(Integer.parseInt(dishID), Integer.parseInt(menuID));
                }

                if (cmi != null && cmi.equalsIgnoreCase("true")) {              // change menu inf
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String mn = request.getParameter("menuname");
                    String sd = request.getParameter("startdate");
                    String ed = request.getParameter("enddate");
                    if (mn != null && !mn.isEmpty()) {
                        md.updateMenuName(Integer.parseInt(menuID), mn);
                    }
                    if (sd != null && l.isDate(sd)) {
                        md.updateMenuStartDate(Integer.parseInt(menuID), sdf.parse(sd));
                    }
                    if (sd != null && l.isDate(ed)) {
                        md.updateMenuEndDate(Integer.parseInt(menuID), sdf.parse(ed));
                    }
                }
                Menu menu = md.getMenuByMenuID(Integer.parseInt(menuID));
                List<Dish> dL = dd.getAllDishInMenu(Integer.parseInt(menuID));
                request.setAttribute("menuInf", menu);
                request.setAttribute("dishList", dL);
                request.getRequestDispatcher("UpdateMenu.jsp").forward(request, response);
                
            }else{
                request.setAttribute("NOMENU", "There is no Menu");
                request.getRequestDispatcher("UpdateMenu.jsp").forward(request, response);
            }

        } catch (ParseException ex) {
            Logger.getLogger(UpdateMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
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
