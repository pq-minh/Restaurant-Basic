/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Ingredient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ultils.MyLib;

/**
 *
 * @author Asus
 */
public class IngredientDao {

    public ArrayList<Ingredient> getIngredient(int dishid) {
        ArrayList<Ingredient> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = MyLib.makeConnection();
            if (cn != null) {
                String sql = "select i.IngredientID, i.IngredientName, d.Quantity, i.Image \n"
                        + "from Ingredient i inner join MakeDish d on i.IngredientID = d.IngredientID \n"
                        + "where d.DishID = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, dishid);
                ResultSet rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int ingredientid = rs.getInt("IngredientID");
                        String ingredientname = rs.getString("IngredientName");
                        int ingredientquantity = rs.getInt("Quantity");
                        String ingredientimg = rs.getString("Image");
                        list.add(new Ingredient(ingredientid, ingredientname, ingredientquantity, ingredientimg));
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

    public List<Ingredient> getAllIngredient() {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Ingredient";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IngredientID");
                String name = rs.getString("IngredientName");
                int quantity = rs.getInt("Quantity");
                String img = rs.getString("Image");
                Ingredient ingredient = new Ingredient(id, name, quantity, img);
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

        return ingredients;
    }

    public String getIngredientNameByID(int id) {
        String name = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT IngredientName FROM Ingredient WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("IngredientName");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return name;
    }

    public List<Ingredient> getAllIngredientByDishID(int id) {
        List<Ingredient> ing = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT i.IngredientID, i.IngredientName, md.Quantity "
                    + "FROM MakeDish md "
                    + "JOIN Ingredient i ON md.IngredientID = i.IngredientID "
                    + "WHERE md.DishID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                int iID = rs.getInt("IngredientID");
                String name = rs.getString("IngredientName");
                int quantity = rs.getInt("Quantity");
                String img = rs.getString("IngredientName");
                Ingredient ingredient = new Ingredient(iID, name,  quantity, img);
                ing.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return ing;
    }

    public void updateIngredientQuantityToMakeDish(int dID, int iID, int quantity) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE MakeDish SET Quantity = ? WHERE DishID = ? AND IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, dID);
            ps.setInt(3, iID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void removeIngredientFromDish(int dID, int iID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "DELETE FROM MakeDish WHERE DishID = ? AND IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dID);
            ps.setInt(2, iID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getIngredientQuantityInStock(int iID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int quantity = 0;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT Quantity FROM Ingredient WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, iID);
            rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return quantity;
    }

    public void addIngredientToDish(int dID, int iID, int quantity) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO MakeDish (DishID, IngredientID, Quantity) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dID);
            ps.setInt(2, iID);
            ps.setInt(3, quantity);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public Ingredient getIngredientInDish(int dID, int iID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Ingredient ing = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT i.IngredientID, i.IngredientName, md.Quantity "
                    + "FROM Ingredient i "
                    + "INNER JOIN MakeDish md ON i.IngredientID = md.IngredientID "
                    + "WHERE md.DishID = ? AND md.IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dID);
            ps.setInt(2, iID);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("IngredientID");
                String name = rs.getString("IngredientName");
                int quantity = rs.getInt("Quantity");
                String img = rs.getString("Image");
                ing = new Ingredient(id, name, quantity, img);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {

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
        return ing;
    }

    public void addIngredient(String name, int quantity, String img) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "INSERT INTO Ingredient (IngredientName, Quantity, [Image]) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, quantity);
            ps.setString(3, img);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public Ingredient getIngredientByName(String name) {
        Ingredient ing = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Ingredient WHERE IngredientName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                int iID = rs.getInt("IngredientID");
                String nam = rs.getString("IngredientName");
                int quantity = rs.getInt("Quantity");
                String img = rs.getString("Image");
                ing = new Ingredient(iID, nam, quantity, img);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return ing;
    }

    public Ingredient getIngredientByID(int id) {
        Ingredient ing = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT * FROM Ingredient WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int iID = rs.getInt("IngredientID");
                String name = rs.getString("IngredientName");
                int quantity = rs.getInt("Quantity");
                String img = rs.getString("Image");
                ing = new Ingredient(iID, name, quantity, img);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return ing;
    }

    public void deleteIngredientByID(int iID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "DELETE FROM Ingredient WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, iID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public int countIngredientInMakeDish(int iID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = MyLib.makeConnection();
            String sql = "SELECT COUNT(*) FROM MakeDish WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, iID);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return count;
    }

    public void updateIngredientNameById(int iID, String name) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Ingredient SET IngredientName = ? WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, iID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void updateIngredientQuantity(int iID, int inc, int red) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = MyLib.makeConnection();
            String sql = "UPDATE Ingredient SET Quantity = Quantity + ? - ? WHERE IngredientID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, inc);
            ps.setInt(2, red);
            ps.setInt(3, iID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(IngredientDao.class.getName()).log(Level.SEVERE, null, ex);
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
