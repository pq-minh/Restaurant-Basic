/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Asus
 */
public class Order {
    private int orderID;
    private Date orderDate;
    private float total;
    private int accountID;
    private int status;
    private ArrayList<OrderDetail> detail;

    public Order() {
        orderDate = new Date(System.currentTimeMillis());
        detail = new ArrayList<>();
    }
    
    public Order(int orderID, Date orderdate, int status, float total, int accountID, ArrayList<OrderDetail> detail) {
        this.orderID = orderID;
        this.orderDate = orderdate;
        this.status = status;
        this.total = total;
        this.accountID = accountID;
        this.detail = detail;
    }
    
    public Order(int orderID, Date orderDate, float total, int accountID, int status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.total = total;
        this.accountID = accountID;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public float getTotal() {
        return total;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getStatus() {
        return status;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<OrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<OrderDetail> detail) {
        this.detail = detail;
    }
    
    public boolean addOrderDetail(OrderDetail orderDetail) {
        return detail.add(orderDetail);   
    }
}
