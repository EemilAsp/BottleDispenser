package com.example.bottledispenser;

public class Bottle {
    private String name;
    private double size;
    private double price;

    public Bottle(){}
    public Bottle(String n,  double s, double p){
        name = n;
        price = p;
        size = s;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }
}
