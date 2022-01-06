package com.example.foodkoalaandroid;

import java.io.Serializable;

public class Produs implements Serializable {
    private String name;
    private double price;
    private String des;
    private String img;

    public Produs(String name, double price, String des, String img) {
        this.name = name;
        this.price = price;
        this.des = des;
        this.img = img;
    }
    public Produs() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
