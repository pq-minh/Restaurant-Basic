/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
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
 * @author Asus
 */
public class AccountDao {

    public Account getAccount(String email, String password) {
        Account acc = null;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection(); //tao connection
            if (cn != null) {
                //viet sql va exce
                String sql = "SELECT [AccountID],[Email],[Fullname],[Gender],[DateOfBirth], [Address], [District], [City], [Password],[PhoneNumber], [Role], [Status] FROM [WeeklyMenuDB].[dbo].[Account] where Email=? And Password=? COLLATE SQL_Latin1_General_CP1_CI_AS";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                //buoc 3 doc data
                if (rs != null && rs.next()) {
                    int id = rs.getInt("AccountID");
                    String fullname = rs.getString("Fullname");
                    String gender = rs.getString("Gender");
                    Date datebirth = rs.getDate("DateOfBirth");
                    String address = rs.getString("Address");
                    String district = rs.getString("District");
                    String city = rs.getString("City");
                    String phonenumber = rs.getString("PhoneNumber");
                    String role = rs.getString("Role");
                    int status = rs.getInt("Status");
                    acc = new Account(id, email, fullname, gender, datebirth, address, district, city, password, phonenumber, role, status);
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

        return acc;
    }

    public Account getUser(String email) {
        Account acc = null;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection(); //tao connection
            if (cn != null) {
                //viet sql va exce
                String sql = "SELECT [AccountID],[Email],[Fullname],[Gender],[DateOfBirth], [Address], [District], [City], [Password],[PhoneNumber], [Role], [Status] \n"
                        + "FROM [WeeklyMenuDB].[dbo].[Account] where Email= ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                ResultSet rs = pst.executeQuery();
                //buoc 3 doc data
                if (rs != null && rs.next()) {
                    int id = rs.getInt("AccountID");
                    String fullname = rs.getString("Fullname");
                    String gender = rs.getString("Gender");
                    Date datebirth = rs.getDate("DateOfBirth");
                    String address = rs.getString("Address");
                    String district = rs.getString("District");
                    String city = rs.getString("City");
                    String password = rs.getString("Password");
                    String phonenumber = rs.getString("PhoneNumber");
                    String role = rs.getString("Role");
                    int status = rs.getInt("Status");
                    acc = new Account(id, email, fullname, gender, datebirth, address, district, city, password, phonenumber, role, status);
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

        return acc;
    }

    public int insertUser(String email, String fullname, String password, String phonenumber) {
        int rs = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection(); //tao connection
            if (cn != null) {
                //viet sql va exce
                String sql = "insert into [WeeklyMenuDB].[dbo].[Account] ([Email],[Fullname],[Password],[PhoneNumber], [Role], [Status]) values (?,?,?,?,?,?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, fullname);
                pst.setString(3, password);
                pst.setString(4, phonenumber);
                pst.setString(5, "Customer");
                pst.setInt(6, 1);
                rs = pst.executeUpdate();

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

    public int updatePassword(String newpassword, String email) {
        int rs = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "Update [WeeklyMenuDB].[dbo].[Account] set Password = ? where email=?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, newpassword);
                pst.setString(2, email);
                rs = pst.executeUpdate();
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

    public int updateProfile(String fullname, String phonenumber, String gender, String address, String district, String city, int accid) {
        int rs = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Account]\n"
                        + "SET [Fullname] = ?, PhoneNumber = ?, Gender = ?, [Address] = ?, [District] = ?, [City] = ?\n"
                        + "WHERE AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, fullname);
                pst.setString(2, phonenumber);
                pst.setString(3, gender);
                pst.setString(4, address);
                pst.setString(5, district);
                pst.setString(6, city);
                pst.setInt(7, accid);
                rs = pst.executeUpdate();
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

    public List<Account> getAllCustomerAccountHaveOrder() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> list = new ArrayList<>();

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT DISTINCT a.AccountID, a.Email, a.Fullname, a.Gender, a.DateOfBirth, a.Address, a.District, a.City, a.Password, a.PhoneNumber, a.Role, a.[Status] "
                    + "FROM Account a "
                    + "JOIN Orders o ON a.AccountID = o.AccountID "
                    + "WHERE a.[Status] = 1";
            ps = conn.prepareStatement(sql);
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
                Account account = new Account(id, em, fn, gd, (Date) dob, a, d, c, p, pn, r, s);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void main(String[] args) {
        AccountDao ad = new AccountDao();
        List<Account> l = ad.getAllCustomerAccountHaveOrder();
        System.out.println(l.size());
    }

    public String getRoleActiveAccount(String email, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String role = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT Role FROM Account WHERE Email = ? AND Password = ? AND Status = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                role = rs.getString("Role");
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
        return role;
    }

    public List<Account> getAllAccounts() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> list = new ArrayList<>();

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Account";
            ps = conn.prepareStatement(sql);
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
                Account account = new Account(id, em, fn, gd, (Date) dob, a, d, c, p, pn, r, s);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateStatusAccount(int accountId, int newStatus) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Account SET [Status] = ? WHERE AccountID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, accountId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void changeAccountRole(int accountID, String newRole) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Account SET Role = ? WHERE AccountID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newRole);
            ps.setInt(2, accountID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
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
