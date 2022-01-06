package com.example.foodkoalaandroid;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private ArrayList<Produs> produse;
    private int pret;
    private String nume_proprietar;

    public Cart(ArrayList<Produs> produse, int pret, String nume_proprietar) {
        this.produse = produse;
        this.pret = pret;
        this.nume_proprietar = nume_proprietar;
    }

    public Cart() {
        this.pret = 0;
        this.produse=new ArrayList<>();
    }

    public void addProdus(Produs p)
    {
        produse.add(p);
        pret = (int) (pret + p.getPrice());
    }

    public ArrayList<Produs> getProduse() {
        return produse;
    }

    public void setProduse(ArrayList<Produs> produse) {
        this.produse = produse;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getNume_proprietar() {
        return nume_proprietar;
    }

    public void setNume_proprietar(String nume_proprietar) {
        this.nume_proprietar = nume_proprietar;
    }
}
