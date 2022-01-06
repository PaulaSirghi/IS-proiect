package com.example.foodkoalaandroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private List<Observer> observers = new ArrayList<Observer>();
    private String id;
    private int pret;
    private String status;
    private ArrayList <Produs> produse=new ArrayList<>();
    private String nume_client;
    private String nume_livrator;
    private String adresa;
    private String id_client;
    private String id_livrator;

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getId_livrator() {
        return id_livrator;
    }

    public void setId_livrator(String id_livrator) {
        this.id_livrator = id_livrator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume_client() {
        return nume_client;
    }

    public void setNume_client(String nume_client) {
        this.nume_client = nume_client;
    }

    public String getNume_livrator() {
        return nume_livrator;
    }

    public void setNume_livrator(String nume_livrator) {
        this.nume_livrator = nume_livrator;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Order(int pret, String status, ArrayList<Produs> produse, String numeC, String adresa) {
        this.pret = pret;
        this.status = status;
        this.produse = produse;
        this.nume_client = numeC;
        this.adresa = adresa;
    }

    public Order(int pret, String status, ArrayList<Produs> produse, String numeC, String adresa,String id) {
        this.pret = pret;
        this.status = status;
        this.produse = produse;
        this.nume_client = numeC;
        this.adresa = adresa;
        this.id_client = id;
    }

    public Order() {

    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Produs> getProduse() {
        return produse;
    }

    public void setProduse(ArrayList<Produs> produse) {
        this.produse = produse;
    }

}
