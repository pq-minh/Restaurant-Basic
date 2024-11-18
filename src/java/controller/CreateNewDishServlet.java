/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DishDao;
import dao.IngredientDao;
import dto.Ingredient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class CreateNewDishServlet extends HttpServlet {

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
        System.out.println("Servlet is called");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            DishDao dd = new DishDao();
            Lib l = new Lib();
            IngredientDao id = new IngredientDao();
            HttpSession session = request.getSession();
            List<Ingredient> isL = new ArrayList<>();
            List<Ingredient> aiL = id.getAllIngredient();
            List<String> dishInf = new ArrayList<>();
            
            String dn = request.getParameter("dishname");
            String dt = request.getParameter("dishtype");
            String dp = request.getParameter("dishprice");
            String des = request.getParameter("description");
            String m = request.getParameter("meal");

            String b = request.getParameter("button");
// =============== lưu tt món ăn vào session
            if (l.checkString(dn) && l.checkString(dt) && l.checkString(dp) && l.checkString(des) && l.checkString(m)) {
                if (l.checkString(b) && b.equalsIgnoreCase("Save")) {
                    dishInf.add(dn);
                    dishInf.add(dp);
                    dishInf.add(dt);
                    dishInf.add(des);
                    dishInf.add(m);
                    session.setAttribute("dishInf", dishInf);
                    request.setAttribute("SUCCESS1", "Saved Dish.");
                }
            }

            String ai = request.getParameter("adding");
// ======== create dish
            if (l.checkString(b) && b.equalsIgnoreCase("Create Dish")) {
                isL = (List<Ingredient>) session.getAttribute("ingSessionList");
                dishInf = (List<String>) session.getAttribute("dishInf");
                String img = (String) session.getAttribute("imgname");
                if (isL != null && dishInf != null && dishInf.size() == 5 && !isL.isEmpty() && l.checkString(img)) {
                    img = "Picture/" + img; 
                    Date date = new Date();
                    // name price type des meal
                    // String dishName, String dishType, Date dishDate, int meal, String description, String image, int status
                    dd.addNewDish(dishInf.get(0), dishInf.get(2), date, Integer.parseInt(dishInf.get(4)), dishInf.get(3), img, 1);
                    int ldid = dd.getLastestDishID();
                    dd.addDishPrice(ldid, Float.parseFloat(dishInf.get(1)), date);  // thêm giá  
                    for (Ingredient i : isL) {
                        dd.addMakeDish(ldid, i.getIngredientID(), i.getQuantity());
                    }
                    session.removeAttribute("ingsList");
                    request.setAttribute("SUCCESS2", "Create Dish Successfully.");
                } else {
                    request.setAttribute("FAIL", "Create Dish Fail!");
                }
            }

// ================= luu ảnh vào session
            String in = request.getParameter("imgname");
            if (in != null && !in.isEmpty()) {
                session.setAttribute("imgname", in);
            }

// ================= add ingredient vào session
            if (ai != null && ai.equalsIgnoreCase("true")) {
                String iID = request.getParameter("ingID");
                String q = request.getParameter("quantity");
                if (iID != null && q != null) {
                    int quantity = Integer.parseInt(q);
                    int ingID = Integer.parseInt(iID);
                    boolean c = true;
//                    for (Ingredient i : aiL) {
//                        if (i.getIngredientID() == ingID) {     // ktra tp trong kho vs tp trong session
//                            int q1 = i.getQuantity();
//                            int q2 = Integer.parseInt(quantity);
//                            if (q2 > q1) {
//                                c = false;
//                            }
//                        }
//                    }

                    isL = (List<Ingredient>) session.getAttribute("ingSessionList");
                    if (c) {
                        if (isL == null) {      // nếu lần đầu add tp vô (ch tồn tại) thì tạo mới và add
                            isL = new ArrayList<>();
                            Ingredient ing = new Ingredient(ingID, quantity);
                            isL.add(ing);
                        } else {                        // tồn tại rồi thì chỉ cần add 
                            boolean add = true;
                            for (Ingredient i : isL) {
                                if (i.getIngredientID() == ingID) {                 // những tp nào có rồi thì ko add mà đổi quantity
                                    add = false;
                                    i.setQuantity(quantity);      // có rồi thi đổi quantity
                                    break;
                                }
                            }
                            if (add) {                                      // chưa có thì add
                                Ingredient ing = new Ingredient(ingID, quantity);
                                isL.add(ing);
                            }
                        }

                        if (quantity == 0) {                    // thêm tp mà số lg = 0 thì remove nó khói list
                            for (int i = 0; i < isL.size(); i++) {
                                if (ingID == isL.get(i).getIngredientID()) {
                                    isL.remove(i);
                                }
                            }
                        }

                    }
                    session.setAttribute("ingSessionList", isL);
                }
            }
// =================
            request.setAttribute("allIngList", aiL);
            request.getRequestDispatcher("CreateNewDish.jsp").forward(request, response);
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
