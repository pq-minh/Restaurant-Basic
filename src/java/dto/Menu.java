/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;

/**
 *
 * @author nvhoa
 */
public class Menu {
        private int menuID;
        private String menuName;
        private Date menuStartDate;
        private Date menuEndDate;

    public Menu(int menuID, String menuName, Date menuStartDate, Date menuEndDate) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuStartDate = menuStartDate;
        this.menuEndDate = menuEndDate;
    }
    
    public Menu(String menuName, Date menuStartDate, Date menuEndDate) {
        this.menuName = menuName;
        this.menuStartDate = menuStartDate;
        this.menuEndDate = menuEndDate;
    }

    public int getMenuID() {
        return menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public Date getMenuStartDate() {
        return menuStartDate;
    }

    public Date getMenuEndDate() {
        return menuEndDate;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuStartDate(Date menuStartDate) {
        this.menuStartDate = menuStartDate;
    }

    public void setMenuEndDate(Date menuEndDate) {
        this.menuEndDate = menuEndDate;
    }
        
}

