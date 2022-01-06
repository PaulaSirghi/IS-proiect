package com.example.foodkoalaandroid;

public class Food {
    String name;
    String des;
    String img;
    int price;

    public Food(String name, String des, String img, int price) {
        this.name = name;
        this.des = des;
        this.img = img;
        this.price = price;
    }
    public Food(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
