package com.example.foodkoalaandroid;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String pass;
    private String numar_telefon;
    private String rol;

    public User(String name, String email, String pass, String numar_telefon, String rol) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.numar_telefon = numar_telefon;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumar_telefon() {
        return numar_telefon;
    }

    public void setNumar_telefon(String numar_telefon) {
        this.numar_telefon = numar_telefon;
    }

}

