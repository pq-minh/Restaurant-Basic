/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dao.MenuDao;
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

/**
 *
 * @author nvhoa
 */
public class CreateNewMenuServlet extends HttpServlet {

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
            MenuDao md = new MenuDao();
            DishDao dd = new DishDao();
            HttpSession session = request.getSession();
            String delete = request.getParameter("delete");
            String cm = request.getParameter("createmenu");

            List<String> mList = (List<String>) session.getAttribute("menuList");
            List<String> selectedDish = (List<String>) session.getAttribute("selectedDish");     // chứa ID của Dish

            if (cm != null && cm.equalsIgnoreCase("true")) {
                if (mList.size() != 3 || selectedDish == null || selectedDish.isEmpty()) {
                    request.setAttribute("FAIL", "Create new menu fail.");
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    List<String> mL = (List<String>) session.getAttribute("menuList");
                    Menu menu = new Menu(mL.get(0), sdf.parse(mL.get(1)), sdf.parse(mL.get(2)));
                    md.addNewMenu(menu);
                    int lid = md.getLatestMenuID();
                    //lấy menuID của nó rồi lấy dishID + menuID add vô MakeDish
                    if (selectedDish != null && !selectedDish.isEmpty()) {
                        for (String dish : selectedDish) {
                            int dishID = Integer.parseInt(dish);
                            dd.addDishToMenu(lid, dishID);
                        }
                    }
                    session.removeAttribute("selectedDish");
                    request.setAttribute("SUCCESS", "Create new menu successfully.");
                }
            }

            if (delete != null && delete.equalsIgnoreCase("true")) {
                String id = request.getParameter("id"); // lấy về dishid cần xóa
                if(id != null && selectedDish != null){
                    selectedDish.remove(id);
                }
                session.setAttribute("selectedDish", selectedDish); // Cập nhật lại session với danh sách mới
            }

            if (mList != null) {
                request.getRequestDispatcher("CreateNewMenu.jsp").forward(request, response);
            } else {
                out.println("ko ton tai");
            }
        } catch (ParseException ex) {
            Logger.getLogger(CreateNewMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
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
