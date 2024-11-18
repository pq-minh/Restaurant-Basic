/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
import dto.Dish;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import ultils.MyLib;

/**
 *
 * @author Asus
 */
public class PlanDao {

    public int insertToMakePlan(int dishid, String day, int persid) {
        int rs = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "insert into MakePersonalMenu (DishID, [Day], [PerspID], StatusOfPlan) values (?, ?, ?, ?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, dishid);
                pst.setString(2, day);
                pst.setInt(3, persid);
                pst.setInt(4, 1);
                rs = pst.executeUpdate();
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
        return rs;
    }

    public int insertToPlan(int accid) {
        int rs = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "insert into PersonalPlan ([AccountID]) values (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                rs = pst.executeUpdate();
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
        return rs;
    }

    public int getPersIDFromPlan(int accid) {
        int persid = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select PerspID, AccountID from PersonalPlan where AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    persid = rs.getInt("PerspID");
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
        return persid;
    }

    public int getAccFromPlan(int accid) {
        int accountidfromplan = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select PerspID, AccountID from PersonalPlan where AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    accountidfromplan = rs.getInt("AccountID");
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
        return accountidfromplan;
    }

    public ArrayList<Dish> getDishFromPlan(int accid) {
        ArrayList<Dish> list = new ArrayList<>();
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select a.AccountID, mp.PerspID, mp.DishID, mp.[Day], d.DishName, d.DishType, d.DishDate, d.Meal, d.[Image], d.[Description], d.Status\n"
                        + "from MakePersonalMenu mp inner join dish d on mp.DishID = d.DishID inner join PersonalPlan p on mp.PerspID = p.PerspID inner join Account a on a.AccountID = p.AccountID\n"
                        + "where a.AccountID = ? and mp.[StatusOfPlan] = '1'";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                ResultSet rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int dishid = rs.getInt("DishID");
                        String dishname = rs.getString("DishName");
                        int dishtype = rs.getInt("DishType");
                        Date dishdate = rs.getDate("DishDate");
                        int meal = rs.getInt("Meal");
                        String description = rs.getString("Description");
                        String image = rs.getString("Image");
                        int status = rs.getInt("Status");
                        String day = rs.getString("Day");
                        list.add(new Dish(dishid, dishname, dishtype, dishdate, meal, description, image, status, day));
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

    public int checkDishFromPlan(int dishid, String day, int persid) {
        int rs = 1;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select DishID, [Day], [PerspID], StatusOfPlan from MakePersonalMenu where DishID = ? and [Day] = ? and [PerspID] = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, dishid);
                pst.setString(2, day);
                pst.setInt(3, persid);
                ResultSet rss = pst.executeQuery();
                if (rss != null) {
                    while (rss.next()) {
                        rs = rss.getInt("StatusOfPlan");
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
        return rs;
    }

    public int editDishFormPlan(int dishid, String day, int accid, int status) {
        int rs = 0;
        int perid = 0;
        Connection cn = null;

        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select PerspID, AccountID from PersonalPlan where AccountID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                ResultSet rss = pst.executeQuery();
                if (rss != null) {
                    while (rss.next()) {
                        perid = rss.getInt("PerspID");
                    }
                }

                sql = "update MakePersonalMenu set [StatusOfPlan] = ? where [PerspID] = ? and [DishID] = ? and [Day] = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, status);
                pst.setInt(2, perid);
                pst.setInt(3, dishid);
                pst.setString(4, day);
                rs = pst.executeUpdate();

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
        return rs;
    }

}
