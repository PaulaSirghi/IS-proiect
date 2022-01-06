package com.example.foodkoalaandroid;

public class ClientT {
    private String cont;
    private String adresa;
    private Order order;
    private Cart cart;

    public ClientT() {
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Order getO() {
        return order;
    }

    public void setO(Order o) {
        this.order = o;
    }

    public ClientT(String cont, String adresa) {
        this.cont = cont;
        this.adresa = adresa;
        this.cart = new Cart();
        this.order = new Order();
    }
}
