package com.example.foodkoalaandroid;

public class Livrator {
    private int id;
    private String cont;

    public Livrator(int id,String cont) {
        this.id = id;
        this.cont=cont;
    }
    public Livrator() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
