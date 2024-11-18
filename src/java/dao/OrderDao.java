/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
import dto.Dish;
import dto.Order;
import dto.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylib.Lib;
import ultils.MyLib;

/**
 *
 * @author Asus
 */
public class OrderDao {

    public int saveOrder(int accid, HashMap<Dish, Integer> cart) {
        int result = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            float total = 0;

            for (Dish di : cart.keySet()) {
                total = total + cart.get(di) * di.getDishPrice();
            }

            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "insert into Orders (OrderDate, Total, AccountID, Status) values (?, ?, ?, ?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                pst.setFloat(2, total);
                pst.setInt(3, accid);
                pst.setInt(4, 1);
                result = pst.executeUpdate();

                if (result >= 1) {
                    sql = "select top 1 OrderID from Orders order by OrderID desc";
                    pst = cn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    if (rs != null && rs.next()) {
                        int orderid = rs.getInt("OrderID");
                        for (Dish di : cart.keySet()) {
//                            sql = "select MenuID from MakeMenu where DishID = ?";
//                            pst = cn.prepareStatement(sql);
//                            pst.setInt(1, di.getDishid());
//                            ResultSet rs1 = pst.executeQuery();

                            sql = "insert into OrderDetail (DishID, OrderID, Quantity, Price, Status) values (?, ?, ?, ?, ?)";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, di.getDishID());
                            pst.setInt(2, orderid);
                            pst.setInt(3, cart.get(di));
                            pst.setFloat(4, di.getDishPrice());
                            pst.setInt(5, 1);
                            result = pst.executeUpdate();

                        }
                    }
                    cn.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<OrderDetail> getOrderDetail(int orderid) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        Connection cn = null;

        try {
            cn = MyLib.makeConnection();

            if (cn != null) {
                String sql = "select distinct od.OrderDetailID, od.OrderID, od.DishID, od.MenuID, od.Quantity, od.Price, od.[Status]\n"
                        + "from OrderDetail od inner join Orders o on od.OrderID = o.OrderID where o.OrderID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderid);
                ResultSet rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int orderdtid = rs.getInt("OrderDetailID");
                        int menuid = rs.getInt("MenuID");
                        int dishid = rs.getInt("DishID");
                        int orderid1 = rs.getInt("OrderID");
                        int quantity = rs.getInt("Quantity");
                        Float price = rs.getFloat("Price");
                        int status = rs.getInt("Status");
                        list.add(new OrderDetail(orderdtid, menuid, dishid, orderid1, quantity, price, status));
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

    public ArrayList<Order> getOrder(int accountID) {
        ArrayList<Order> list = new ArrayList<Order>();

        Connection cn = null;

        try {
            cn = MyLib.makeConnection();

            if (cn != null) {

                String sql = "select OrderID, OrderDate, Total, AccountID, Status from Orders where AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accountID);
                ResultSet rs = pst.executeQuery();

                while (rs != null && rs.next()) {
                    int orderid = rs.getInt("OrderID");
                    java.sql.Date orderdate = rs.getDate("OrderDate");
                    Float total = rs.getFloat("Total");
                    int orderstatus = rs.getInt("Status");
                    ArrayList<OrderDetail> listDetail = getOrderDetail(orderid);
                    list.add(new Order(orderid, orderdate, orderstatus, total, accountID, listDetail));
                    
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

    public ArrayList<OrderDetail> getOrderDetails(int orderid, int accid) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = " select od.OrderDetailID, od.OrderID, od.DishID, od.MenuID, od.Quantity, od.Price, o.OrderDate, o.Total, od.Status, o.AccountID, o.Status as OrderStatus\n"
                        + "       from OrderDetail od inner join Orders o on od.OrderID = o.OrderID where od.OrderID = ? AND o.AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderid);
                pst.setInt(2, accid);
                ResultSet rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int orderdtid = rs.getInt("OrderDetailID");
                        int menuid = rs.getInt("MenuID");
                        int dishid = rs.getInt("DishID");
                        int quantity = rs.getInt("Quantity");
                        Float price = rs.getFloat("Price");
                        int stasus = rs.getInt("Status");
                        list.add(new OrderDetail(orderdtid, menuid, dishid, orderid, quantity, price, stasus));
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

//Processing
//Shipped 
//Delivered 
//Cancelled 
//    
//    HashMap, thứ tự của các phần tử không được đảm bảo theo thứ tự mà chúng được thêm vào. 
//  khi lặp qua các mục nhập của HashMap bằng phương thức entrySet(), 
//  có thể nhận được các phần tử theo thứ tự không đúng như bạn mong đợi.
// ko nên sài haspmap mà sài linkedhashmap
    public List<Order> getProcessingOrderByAccountID(int accountID) {
        List<Order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT OrderID, OrderDate, Total, [Status], AccountID FROM Orders WHERE AccountID = ? AND [Status] = 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                java.util.Date orderDate = rs.getDate("OrderDate");
                float total = rs.getFloat("Total");
                int status = rs.getInt("Status");
                // int accountId = rs.getInt("AccountID"); // AccountID không cần thiết vì đã được truyền vào trong điều kiện WHERE

                Order order = new Order(orderId, (java.sql.Date) orderDate, total, status, accountID);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
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

        return orders;
    }

    public Map<String, List<Account>> groupAccountByDistrict(List<Account> list) {
        Map<String, List<Account>> hm = new HashMap<>();

        for (Account a : list) {
            String district = a.getDistrict();
            if (!hm.containsKey(district)) {
                hm.put(district, new ArrayList<>());
            }
            hm.get(district).add(a);
        }
        return hm;
    }

    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT OrderDetailID, MenuID, DishID, OrderID, Quantity, Price, [Status] FROM OrderDetail WHERE OrderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int OrderDetailID = rs.getInt("OrderDetailID");
                int menuID = rs.getInt("MenuID");
                int dishID = rs.getInt("DishID");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                int status = rs.getInt("Status");
                OrderDetail detail = new OrderDetail(OrderDetailID, menuID, dishID, orderID, quantity, price, status);
                orderDetails.add(detail);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
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

        return orderDetails;
    }

    public List<Order> getOrderByAccountID(int accountID) {
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT OrderID, OrderDate, Total, [Status], AccountID FROM Orders WHERE AccountID = ? AND [Status] > 0 ORDER BY OrderDate DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                java.util.Date orderDate = rs.getDate("OrderDate");
                float total = rs.getFloat("Total");
                int status = rs.getInt("Status");

                Order order = new Order(orderId, (java.sql.Date) orderDate, total, status, accountID);
                list.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void changeOrderStatus(int orderID, int status) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Orders SET [Status] = ? WHERE OrderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, orderID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void changeOrderDetailStatus(int OrderDetailID, int status) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE OrderDetail SET [Status] = ? WHERE OrderDetailID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, OrderDetailID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<Account> searchOrderByPhoneNumber(String phoneNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> list = new ArrayList<>();

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT DISTINCT a.* "
                    + "FROM Account a "
                    + "JOIN Orders o ON a.AccountID = o.AccountID "
                    + "WHERE a.PhoneNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("AccountID");
                String em = rs.getString("Email");
                String fn = rs.getString("Fullname");
                String gd = rs.getString("Gender");
                java.util.Date dob = rs.getDate("DateOfBirth");
                String a = rs.getString("Address");
                String d = rs.getString("District");
                String c = rs.getString("City");
                String p = rs.getString("Password");
                String pn = rs.getString("PhoneNumber");
                String r = rs.getString("Role");
                int s = rs.getInt("Status");
                Account account = new Account(id, em, fn, gd, (java.sql.Date) dob, a, d, c, p, pn, r, s);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Account> searchOrderByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> list = new ArrayList<>();

        try {

            conn = MyLib.makeConnection();
            String sql = "SELECT DISTINCT a.* "
                    + "FROM Account a "
                    + "JOIN Orders o ON a.AccountID = o.AccountID "
                    + "WHERE a.Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("AccountID");
                String emailResult = rs.getString("Email");
                String fullname = rs.getString("Fullname");
                String gender = rs.getString("Gender");
                java.util.Date dateOfBirth = rs.getDate("DateOfBirth");
                String address = rs.getString("Address");
                String district = rs.getString("District");
                String city = rs.getString("City");
                String password = rs.getString("Password");
                String phoneNumber = rs.getString("PhoneNumber");
                String role = rs.getString("Role");
                int status = rs.getInt("Status");

                Account account = new Account(accountId, emailResult, fullname, gender, (java.sql.Date) dateOfBirth, address, district, city, password, phoneNumber, role, status);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Order> searchOrderDate(List<Order> list, java.util.Date date) throws ParseException {
        Lib ml = new Lib();
        OrderDao od = new OrderDao();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String date1 = sdf.format(date);

        for (int i = list.size() - 1; i >= 0; i--) {
            String date2 = list.get(i).getOrderDate().toString();
            if (ml.compareDateString(date1, date2) != 0) {
                list.remove(i);
            }
        }
        return list;
    }
}
