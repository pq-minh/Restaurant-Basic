/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Account;
import dto.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ultils.MyLib;

/**
 *
 * @author nvhoa
 */
public class MenuDao {

//    public List<Account> getAllMenuAccounts() {
//        List<Account> list = new ArrayList<>();
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            conn = MyLib.makeConnection();
//            String sql = "SELECT DISTINCT a.AccountID, a.Email, a.Fullname, a.Gender, a.DateOfBirth, a.District, a.Password, a.PhoneNumber, a.Role, a.[Status] "
//                    + "FROM Account a "
//                    + "INNER JOIN Orders o ON a.AccountID = o.AccountID "
//                    + "WHERE a.Role = ? AND a.[Status] = 1";
//
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, "Customer"); // Chỉ lấy các tài khoản có vai trò là Customer
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("AccountID");
//                String em = rs.getString("Email");
//                String fn = rs.getString("Fullname");
//                String gd = rs.getString("Gender");
//                Date dob = rs.getDate("DateOfBirth");
//                String a = rs.getString("Address");
//                String d = rs.getString("District");
//                String c = rs.getString("City");
//                String p = rs.getString("Password");
//                String pn = rs.getString("PhoneNumber");
//                String r = rs.getString("Role");
//                int s = rs.getInt("Status");
//                Account account = new Account(id, em, fn, gd, dob, a, d, c, p, pn, r, s);
//                list.add(account);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return customers;
//    }

    public List<Menu> getAllMenu() {
        List<Menu> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT MenuID, MenuName, MenuStartDate, MenuEndDate FROM WeeklyMenu";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int menuID = rs.getInt("MenuID");
                String menuName = rs.getString("MenuName");
                Date menuStartDate = rs.getDate("MenuStartDate");
                Date menuEndDate = rs.getDate("MenuEndDate");
                Menu menu = new Menu(menuID, menuName, menuStartDate, menuEndDate);
                list.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static void main(String[] args) {
        MenuDao md = new MenuDao();
        List<Menu> list = md.getAllMenu();
        for (Menu menu : list) {
            System.out.println(menu.getMenuName());
            System.out.println(menu.getMenuEndDate());
        }
    }

    public Menu getMenuByMenuID(int menuID) {
        Menu menu = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM WeeklyMenu WHERE MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menuID);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("MenuID");
                String name = rs.getString("MenuName");
                Date startDate = rs.getDate("MenuStartDate");
                Date endDate = rs.getDate("MenuEndDate");
                menu = new Menu(id, name, startDate, endDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return menu;
    }

    public void addNewMenu(Menu menu) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO WeeklyMenu (MenuName, MenuStartDate, MenuEndDate) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, menu.getMenuName());
            ps.setDate(2, new java.sql.Date(menu.getMenuStartDate().getTime()));
            ps.setDate(3, new java.sql.Date(menu.getMenuEndDate().getTime()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getLatestMenuID() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int menuID = -1;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT TOP 1 MenuID FROM WeeklyMenu ORDER BY MenuID DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                menuID = rs.getInt(1);
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
        return menuID;
    }

    public void updateMenuName(int menuID, String name) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE WeeklyMenu SET MenuName = ? WHERE MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, menuID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void updateMenuStartDate(int menuID, Date startdate) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE WeeklyMenu SET MenuStartDate = ? WHERE MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startdate.getTime()));
            ps.setInt(2, menuID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void updateMenuEndDate(int menuID, Date enddate) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE WeeklyMenu SET MenuEndDate = ? WHERE MenuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(enddate.getTime()));
            ps.setInt(2, menuID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
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
