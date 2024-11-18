/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Asus
 */
public class OrderDetail {
    private int detailID;
    private int menuID;
    private int dishID;
    private int orderID;
    private int quantity;
    private float price;
    private int status;

    public OrderDetail(int detailID, int menuID, int dishID, int orderID, int quantity, float price, int status) {
        this.detailID = detailID;
        this.menuID = menuID;
        this.dishID = dishID;
        this.orderID = orderID;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public int getDetailID() {
        return detailID;
    }

    public int getMenuID() {
        return menuID;
    }

    public int getDishID() {
        return dishID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
