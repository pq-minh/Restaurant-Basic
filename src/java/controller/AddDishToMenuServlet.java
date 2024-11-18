/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dto.Dish;
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
public class AddDishToMenuServlet extends HttpServlet {

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
            String id = request.getParameter("id");

            DishDao dd = new DishDao();
            List<Dish> dList = new ArrayList<>();
            HttpSession session = request.getSession();

//request - dishList   : lấy từ servlet /all hc dc search/ request
//session - dishToMenu :  lưu dishid dc chọn
//lúc gửi dishid từ trang adddishtomenu lưu nó vô dishToMenu
            if (id != null) {
                List<String> selectedDish = (List<String>) session.getAttribute("selectedDish");
                if (selectedDish == null) {    // chua dc tao
                    List<String> sdL = new ArrayList<>();
                    sdL.add(id);
                    session.setAttribute("selectedDish", sdL);
                } else {  // co roi thi chi can add 
                    if (!selectedDish.contains(id)) {        // ko add phan tu da tồn tại
                        selectedDish.add(id);
                    }
                    session.setAttribute("selectedDish", selectedDish);
                }
                
                String sk = (String) session.getAttribute("searchDishKey");
                if (sk != null && !sk.isEmpty()) {
                    dList = dd.getAllAvailableDishByDishName(sk);
                } else {
                    dList = dd.getAllAvailableDish();
                }
                request.setAttribute("dishList", dList);
                request.getRequestDispatcher("AddDishToMenu.jsp").forward(request, response);
            } else {
                dList = dd.getAllAvailableDish();
                request.setAttribute("dishList", dList);
                request.getRequestDispatcher("AddDishToMenu.jsp").forward(request, response);
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
