package com.example.foodkoalaandroid;

public class ObserverClient implements Observer{
    @Override
    public void update(String mesaj) {
        System.out.println(mesaj);
    }
}
