/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Dish;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import ultils.MyLib;

/**
 *
 * @author Asus
 */
public class DishDao {

    public ArrayList<Dish> getDishs(String findname) {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                        + "FROM [dbo].[Dish] d \n"
                        + "INNER JOIN (\n"
                        + "    SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                        + "    FROM [dbo].[Dish_Price]\n"
                        + "    GROUP BY DishID\n"
                        + ") latest_dp ON d.DishID = latest_dp.DishID\n"
                        + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                        + "WHERE d.DishName COLLATE Vietnamese_CI_AI LIKE ? AND d.Status = '1'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + findname + "%");
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int dishid = table.getInt("DishID");
                        String dishname = table.getString("DishName");
                        int dishtype = table.getInt("DishType");
                        Date disDate = table.getDate("DishDate");
                        int meal = table.getInt("Meal");
                        Float dishprice = table.getFloat("PriceDish");
                        String dishimg = table.getString("Image");
                        int status = table.getInt("Status");
                        list.add(new Dish(dishid, dishname, dishprice, dishtype, disDate, meal, dishimg, status));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null);
                {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Dish> sortDishLetter(ArrayList<Dish> list) {
        Collator collator = Collator.getInstance(Locale.getDefault());

        Collections.sort(list, new Comparator<Dish>() {
            @Override
            public int compare(Dish d1, Dish d2) {
                return collator.compare(d1.getDishName(), d2.getDishName());
            }
        });

        return list;
    }

    public ArrayList<Dish> sortDishPriceAsc(ArrayList<Dish> list, boolean b) {
        Collections.sort(list, new Comparator<Dish>() {
            @Override
            public int compare(Dish d1, Dish d2) {
                if (b) {
                    return Float.compare(d1.getDishPrice(), d2.getDishPrice());
                } else {
                    return Float.compare(d2.getDishPrice(), d1.getDishPrice());
                }
            }
        });
        return list;
    }

    public Dish getDish(int dishid) {
        Dish rs = null;
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                        + "                        FROM [dbo].[Dish] d \n"
                        + "                        INNER JOIN (SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate FROM [dbo].[Dish_Price] GROUP BY DishID) latest_dp ON d.DishID = latest_dp.DishID\n"
                        + "                        INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                        + "                        WHERE d.DishID = ? AND d.Status = '1'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, dishid);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        String dishname = table.getString("DishName");
                        int dishtype = table.getInt("DishType");
                        Date dishdate = table.getDate("DishDate");
                        int meal = table.getInt("Meal");
                        Float price = table.getFloat("PriceDish");
                        String imageurl = table.getString("Image");
                        int status = table.getInt("Status");
                        rs = new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public ArrayList<Dish> getDishfromorderdetail(int orderid, int accid) {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], od.Price, od.OrderID, o.AccountID\n"
                        + "FROM [dbo].[Dish] d INNER JOIN  OrderDetail od ON d.DishID = od.DishID inner join Orders o on o.OrderID = od.OrderID\n"
                        + "WHERE od.OrderID = ? AND o.AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderid);
                pst.setInt(2, accid);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int dishid = table.getInt("DishID");
                        String dishname = table.getString("DishName");
                        int dishtype = table.getInt("DishType");
                        Date dishdate = table.getDate("DishDate");
                        int meal = table.getInt("Meal");
                        Float price = table.getFloat("Price");
                        String imageurl = table.getString("Image");
                        int status = table.getInt("Status");
                        list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Dish> getDishMeal(int meal) {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                        + "FROM [dbo].[Dish] d \n"
                        + "INNER JOIN (\n"
                        + "    SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                        + "    FROM [dbo].[Dish_Price]\n"
                        + "    GROUP BY DishID\n"
                        + ") latest_dp ON d.DishID = latest_dp.DishID\n"
                        + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                        + "WHERE d.Meal = ? AND d.Status = '1'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, meal);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int dishid = table.getInt("DishID");
                        String dishname = table.getString("DishName");
                        int dishtype = table.getInt("DishType");
                        Date dishdate = table.getDate("DishDate");
                        Float price = table.getFloat("PriceDish");
                        String imageurl = table.getString("Image");
                        int status = table.getInt("Status");
                        list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Dish> getDishTypeMeal(int dishtype, int meal, String week, String disname) {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = null;
                if (week == null || week.isEmpty() && disname == null) {
                    sql = "DECLARE @Meal INT =  ? SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                            + "FROM [dbo].[Dish] d \n"
                            + "INNER JOIN (\n"
                            + "    SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                            + "    FROM [dbo].[Dish_Price]\n"
                            + "    GROUP BY DishID\n"
                            + ") latest_dp ON d.DishID = latest_dp.DishID\n"
                            + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                            + "WHERE d.DishType = ? AND (d.Meal = CASE WHEN @Meal = 0 THEN d.Meal ELSE @Meal END) AND d.Status = '1'";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, meal);
                    pst.setInt(2, dishtype);
                    ResultSet table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int dishid = table.getInt("DishID");
                            String dishname = table.getString("DishName");
                            Date dishdate = table.getDate("DishDate");
                            Float price = table.getFloat("PriceDish");
                            String imageurl = table.getString("Image");
                            int status = table.getInt("Status");
                            list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                        }
                    }
                } else if (disname != null) {
                    sql = "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                            + "FROM [dbo].[Dish] d \n"
                            + "INNER JOIN (\n"
                            + "SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                            + "FROM [dbo].[Dish_Price]\n"
                            + "GROUP BY DishID)\n"
                            + "latest_dp ON d.DishID = latest_dp.DishID\n"
                            + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                            + "WHERE d.DishType = ? AND d.DishName COLLATE Vietnamese_CI_AI LIKE ? AND d.Status = '1'";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, dishtype);
                    pst.setString(2, "%" + disname + "%");
                    ResultSet table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int dishid = table.getInt("DishID");
                            String dishname = table.getString("DishName");
                            Date dishdate = table.getDate("DishDate");
                            Float price = table.getFloat("PriceDish");
                            String imageurl = table.getString("Image");
                            int status = table.getInt("Status");
                            list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                        }
                    }
                } else {
                    sql = "DECLARE @CurrentDate DATE = GETDATE();\n"
                            + "DECLARE @MenuID INT;\n"
                            + "\n"
                            + "SELECT @MenuID = MenuID\n"
                            + "FROM WeeklyMenu\n"
                            + "WHERE @CurrentDate BETWEEN MenuStartDate AND MenuEndDate;\n"
                            + "\n"
                            + "IF @MenuID IS NULL\n"
                            + "BEGIN\n"
                            + "    SELECT TOP 1 @MenuID = MenuID\n"
                            + "    FROM WeeklyMenu\n"
                            + "    WHERE @CurrentDate > MenuEndDate\n"
                            + "    ORDER BY MenuEndDate DESC;\n"
                            + "END;\n"
                            + "\n"
                            + "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                            + "FROM [dbo].[Dish] d\n"
                            + "INNER JOIN (\n"
                            + "    SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                            + "    FROM [dbo].[Dish_Price]\n"
                            + "    GROUP BY DishID\n"
                            + ") latest_dp ON d.DishID = latest_dp.DishID\n"
                            + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                            + "INNER JOIN [dbo].[MakeMenu] mm ON d.DishID = mm.DishID\n"
                            + "WHERE mm.MenuID = @MenuID\n"
                            + "  AND d.DishType = ?\n"
                            + "  AND d.Status = '1';";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, dishtype);
                    ResultSet table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int dishid = table.getInt("DishID");
                            String dishname = table.getString("DishName");
                            Date dishdate = table.getDate("DishDate");
                            Float price = table.getFloat("PriceDish");
                            String imageurl = table.getString("Image");
                            int status = table.getInt("Status");
                            list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Dish> getDishForMenu() {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "DECLARE @CurrentDate DATE = GETDATE();\n"
                        + "DECLARE @MenuID INT;\n"
                        + "\n"
                        + "SELECT @MenuID = MenuID\n"
                        + "FROM WeeklyMenu\n"
                        + "WHERE @CurrentDate BETWEEN MenuStartDate AND MenuEndDate;\n"
                        + "\n"
                        + "IF @MenuID IS NULL\n"
                        + "BEGIN\n"
                        + "    SELECT TOP 1 @MenuID = MenuID\n"
                        + "    FROM WeeklyMenu\n"
                        + "    WHERE @CurrentDate > MenuEndDate\n"
                        + "    ORDER BY MenuEndDate DESC;\n"
                        + "END;\n"
                        + "\n"
                        + "SELECT d.[DishID], d.[DishName], d.[DishType], d.[DishDate], d.[Meal], d.[Image], d.[Status], dp.PriceDish, dp.PriceStartDate\n"
                        + "FROM [dbo].[Dish] d\n"
                        + "INNER JOIN (\n"
                        + "    SELECT DishID, MAX(PriceStartDate) AS LatestPriceDate\n"
                        + "    FROM [dbo].[Dish_Price]\n"
                        + "    GROUP BY DishID\n"
                        + ") latest_dp ON d.DishID = latest_dp.DishID\n"
                        + "INNER JOIN [dbo].[Dish_Price] dp ON d.DishID = dp.DishID AND dp.PriceStartDate = latest_dp.LatestPriceDate\n"
                        + "INNER JOIN [dbo].[MakeMenu] mm ON d.DishID = mm.DishID\n"
                        + "WHERE mm.MenuID = @MenuID\n"
                        + "  AND d.Status = '1';";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int dishid = table.getInt("DishID");
                        String dishname = table.getString("DishName");
                        Date dishdate = table.getDate("DishDate");
                        Float price = table.getFloat("PriceDish");
                        int dishtype = table.getInt("DishType");
                        int meal = table.getInt("Meal");
                        String imageurl = table.getString("Image");
                        int status = table.getInt("Status");
                        list.add(new Dish(dishid, dishname, price, dishtype, dishdate, meal, imageurl, status));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Dish> getAllAvailableDish() {
        List<Dish> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Dish WHERE [Status] = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                Dish dish = new Dish(id, dn, dt, dd, m, des, img, st);
                list.add(dish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Dish> getAllDish() {
        List<Dish> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Dish";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                Dish dish = new Dish(id, dn, dt, dd, m, des, img, st);
                list.add(dish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Dish> getAllAvailableDishByDishName(String dishname) {
        List<Dish> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Dish WHERE [Status] = 1 AND DishName LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + dishname + "%");  // Sử dụng chuỗi tìm kiếm với ký tự đại diện
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                Dish dish = new Dish(id, dn, dt, dd, m, des, img, st);
                list.add(dish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Dish getAvailableDishByDishID(int dishID) {
        Dish dish = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Dish WHERE [Status] = 1 AND DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);  // Sử dụng chuỗi tìm kiếm với ký tự đại diện
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                dish = new Dish(id, dn, dt, dd, m, des, img, st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dish;
    }

    public Dish getDishByDishID(int dishID) {
        Dish dish = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Dish WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);  // Sử dụng chuỗi tìm kiếm với ký tự đại diện
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                dish = new Dish(id, dn, dt, dd, m, des, img, st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dish;
    }

    public List<Dish> getAllDishInMenu(int menuID) {
        List<Dish> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT d.* FROM Dish d JOIN MakeMenu mm ON d.DishID = mm.DishID WHERE mm.MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menuID);  // Thiết lập giá trị cho MenuID
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("DishID");
                String dn = rs.getString("DishName");
                int dt = rs.getInt("DishType");
                Date dd = rs.getDate("DishDate");
                int m = rs.getInt("Meal");
                String des = rs.getString("Description");
                String img = rs.getString("Image");
                int st = rs.getInt("Status");
                Dish dish = new Dish(id, dn, dt, dd, m, des, img, st);
                list.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void removeDishFromMenu(int dishID, int menuID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "DELETE FROM MakeMenu WHERE DishID = ? AND MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);
            ps.setInt(2, menuID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// thêm món ăn có sẵn vào menu
    public void addDishToMenu(int menuID, int dishID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO MakeMenu (MenuID, DishID) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menuID);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//============================== add dish vào database
    public void addNewDish(String dishName, String dishType, Date dishDate, int meal, String description, String image, int status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO Dish (DishName, DishType, DishDate, Meal, [Description], [Image], [Status]) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dishName);
            ps.setString(2, dishType);
            ps.setDate(3, new java.sql.Date(dishDate.getTime()));
            ps.setInt(4, meal);
            ps.setString(5, description);
            ps.setString(6, image);
            ps.setInt(7, status);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDishPrice(int dishID, float priceDish, Date priceStartDate) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO Dish_Price (DishID, PriceDish, PriceStartDate, PriceEndDate) VALUES (?, ?, ?, NULL)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);
            ps.setFloat(2, priceDish);
            ps.setDate(3, new java.sql.Date(priceStartDate.getTime()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addIngredient(String ingredientName, int quantity) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO Ingredient (IngredientName, Quantity) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, ingredientName);
            ps.setInt(2, quantity);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMakeDish(int dishID, int ingredientID, int quantity) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO MakeDish (IngredientID, Quantity, DishID) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ingredientID);
            ps.setInt(2, quantity);
            ps.setInt(3, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getLastestDishID() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int dishID = -1;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT TOP 1 DishID FROM Dish ORDER BY DishID DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                dishID = rs.getInt("DishID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dishID;
    }

//==============================================================
    public Float getCurrentPriceByDishID(int dishID) {      // lấy gia món ăn hiện tại
        Float price = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT PriceDish FROM Dish_Price WHERE DishID = ? AND PriceStartDate <= GETDATE() "
                    + "AND (PriceEndDate IS NULL OR PriceEndDate >= GETDATE())";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);
            rs = ps.executeQuery();

            if (rs.next()) {
                price = rs.getFloat("PriceDish");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    public void updateDishStatus(int dishID, int status) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET [Status] = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDishName(int dishID, String name) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET DishName = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, dishID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDishType(int dishID, int type) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET DishType = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, type);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMeal(int dishID, int meal) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET Meal = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, meal);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDescription(int dishID, String des) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET [Description] = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, des);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateImage(int dishID, String img) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish SET [Image] = ? WHERE DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, img);
            ps.setInt(2, dishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// Cập nhật ngày kết thúc của giá hiện tại
    public void updatePriceEndDate(int dishID, Date enddate) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Dish_Price SET PriceEndDate = ? WHERE DishID = ? AND PriceEndDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(enddate.getTime()));
            ps.setInt(2, dishID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// Thêm giá mới vào bảng Dish_Price
    public void addNewDishPrice(int dishID, float price, Date date) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO Dish_Price (DishID, PriceDish, PriceStartDate) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishID);
            ps.setFloat(2, price);
            ps.setDate(3, new java.sql.Date(date.getTime()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DishDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
