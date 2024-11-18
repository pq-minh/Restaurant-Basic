/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dao.IngredientDao;
import dto.Dish;
import dto.Ingredient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
public class UpdateDishServlet extends HttpServlet {

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
            IngredientDao id = new IngredientDao();
            Lib l = new Lib();
            String dishID = request.getParameter("dishID");

            String u = request.getParameter("update");
            String d = request.getParameter("delete");
            String a = request.getParameter("apply");
            String cq = request.getParameter("changequantity");
            HttpSession session = request.getSession();

            List<Ingredient> aiL = id.getAllIngredient();
            request.setAttribute("aiList", aiL);

            if (dishID != null && d != null && d.equalsIgnoreCase("true")) {
                dd.updateDishStatus(Integer.parseInt(dishID), 0);
            }

            if (dishID != null && a != null && a.equalsIgnoreCase("true")) {
                dd.updateDishStatus(Integer.parseInt(dishID), 1);
            }

            if (dishID != null && u != null && u.equalsIgnoreCase("true")) {
                session.setAttribute("dishID", dishID);
                request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
            }

//=============================
            String dishIDSession = (String) session.getAttribute("dishID");
            if (dishIDSession != null) {
                int dID = Integer.parseInt(dishIDSession);
                String c = request.getParameter("changeinf");
                if (c != null && c.equalsIgnoreCase("true")) {
                    String dn = request.getParameter("dishname");
                    String dt = request.getParameter("dishtype");
                    String dp = request.getParameter("dishprice");
                    String des = request.getParameter("description");
                    String meal = request.getParameter("meal");
                    if (l.checkString(dn)) {
                        dd.updateDishName(dID, dn);
                    }
                    if (l.checkString(dt) && Integer.parseInt(dt) != 0) {
                        dd.updateDishType(dID, Integer.parseInt(dt));
                    }
                    if (l.checkString(dp)) {
                        Date date = new Date();
                        dd.updatePriceEndDate(dID, date);
                        dd.addNewDishPrice(dID, Float.parseFloat(dp), date);
                    }
                    if (l.checkString(des)) {
                        dd.updateDescription(dID, des);
                    }
                    if (l.checkString(meal)) {
                        dd.updateMeal(dID, Integer.parseInt(meal));
                    }
                    request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
                }
//==================================
                String ingID = request.getParameter("ingID");
                String q = request.getParameter("quantity");
                if (l.checkString(cq) && cq.equalsIgnoreCase("true")) {
                    if (l.checkString(q) && Integer.parseInt(q) > 0) {
                        id.updateIngredientQuantityToMakeDish(dID, Integer.parseInt(ingID), Integer.parseInt(q));
                    }
                    request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
                }
//==================================
//thêm tp vào món ăn
                String ai = request.getParameter("adding");
                if (l.checkString(ai) && ai.equalsIgnoreCase("true")) {
                    if (l.checkString(q) && Integer.parseInt(q) > 0) {
                        Ingredient ing = id.getIngredientInDish(dID, Integer.parseInt(ingID));
                        if (ing == null) {
                            id.addIngredientToDish(dID, Integer.parseInt(ingID), Integer.parseInt(q));
                        }
                    }
                    request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
                }
//==================================
//xóa tp khỏi món ăn
                String ri = request.getParameter("removeing");
                if (l.checkString(ri) && ri.equalsIgnoreCase("true")) {
                    id.removeIngredientFromDish(dID, Integer.parseInt(ingID));
                    request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
                }

//==================================
//đổi ảnh món ăn
                String img = request.getParameter("imgname");
                if (l.checkString(img)) {
                    img = "Picture/" + img;
                    dd.updateImage(dID, img);
                    request.getRequestDispatcher("UpdateDish.jsp").forward(request, response);
                }

//==================================
            }
            List<Dish> adL1 = dd.getAllDish();
            request.setAttribute("adList1", adL1);
            request.getRequestDispatcher("ShowAllDish.jsp").forward(request, response);

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
