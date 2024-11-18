/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Dish {
    private int dishID;
    private String dishName;
    private float dishPrice;
    private int dishType;
    private Date dishDate;
    private int meal;
    private String description;
    private String image;
    private int status;
    private String day;

    public Dish(int dishID, String dishName, int dishType, Date dishDate, int meal, String description, String image, int status) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishDate = dishDate;
        this.meal = meal;
        this.description = description;
        this.image = image;
        this.status = status;
    }
    
    public Dish(int dishID, String dishName, float dishPrice, int dishType, Date dishDate, int meal, String description, String image, int status) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishType = dishType;
        this.dishDate = dishDate;
        this.meal = meal;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public Dish(int dishID, String dishName, float dishPrice, int dishType, Date dishDate, int meal, String image, int status) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishType = dishType;
        this.dishDate = dishDate;
        this.meal = meal;
        this.image = image;
        this.status = status;
    }

    public Dish(int dishID, String dishName, int dishType, Date dishDate, int meal, String description, String image, int status, String day) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishDate = dishDate;
        this.meal = meal;
        this.description = description;
        this.image = image;
        this.status = status;
        this.day = day;
    }
    
    public int getDishID() {
        return dishID;
    }

    public String getDishName() {
        return dishName;
    }
    
    public float getDishPrice() {
        return dishPrice;
    }

    public int getDishType() {
        return dishType;
    }

    public Date getDishDate() {
        return dishDate;
    }

    public int getMeal() {
        return meal;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getStatus() {
        return status;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPice(float dishPice) {
        this.dishPrice = dishPice;
    }
    
    public void setDishType(int dishType) {
        this.dishType = dishType;
    }

    public void setDishDate(Date dishDate) {
        this.dishDate = dishDate;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
