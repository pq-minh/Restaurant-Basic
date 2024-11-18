/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.IngredientDao;
import dto.Ingredient;
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
public class UpdateIngredientServlet extends HttpServlet {

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
            IngredientDao id = new IngredientDao();
            String ingID = request.getParameter("ingID");
            HttpSession session = request.getSession();
            

            List<Ingredient> aiL = id.getAllIngredient();

// ============== xóa tp
            String d = request.getParameter("delete");
            if (d != null && d.equalsIgnoreCase("true")) {
                // nếu số lg tp = 0 và ko có món ăn nào áp dụng tp thì dc xóa
                int a = id.getIngredientQuantityInStock(Integer.parseInt(ingID));
                int b = id.countIngredientInMakeDish(Integer.parseInt(ingID));
                if (a == 0 && b == 0) {
                    id.deleteIngredientByID(Integer.parseInt(ingID));
                } else {
                    request.setAttribute("FAIL", "You can only remove ingredient when there are no more quantity left in stock and there is no use of ingredient.");
                }
            }

// ============== update tp
            String u = request.getParameter("update");
            if (u != null && u.equalsIgnoreCase("true")) {
// ấn nút vô trang update thì đưa id lên session
                session.setAttribute("ingID", ingID);
                request.getRequestDispatcher("UpdateIngredient.jsp").forward(request, response);
            }

            String c = request.getParameter("change");
            String ingIDSession = (String) session.getAttribute("ingID");
            if (c != null && c.equalsIgnoreCase("true")) {
                String in = request.getParameter("ingname");
                String inc = request.getParameter("inc");
                String red = request.getParameter("red");
                if (in != null && !in.isEmpty()) {
                    id.updateIngredientNameById(Integer.parseInt(ingIDSession), in);
                }
                boolean a = (inc != null && !inc.isEmpty());
                boolean b = (red != null && !red.isEmpty());
                if (a && b == false) {
                    id.updateIngredientQuantity(Integer.parseInt(ingIDSession), Integer.parseInt(inc), 0);
                }
                if (b && a == false) {
                    id.updateIngredientQuantity(Integer.parseInt(ingIDSession), 0, Integer.parseInt(red));
                }
                if (b && a) {
                    id.updateIngredientQuantity(Integer.parseInt(ingIDSession), Integer.parseInt(inc), Integer.parseInt(red));
                }
                request.getRequestDispatcher("UpdateIngredient.jsp").forward(request, response);
            }

            request.setAttribute("aiList", aiL);
            request.getRequestDispatcher("ShowAllIngredient.jsp").forward(request, response);
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
