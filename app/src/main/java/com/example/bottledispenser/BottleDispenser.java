package com.example.bottledispenser;


import android.widget.TextView;

import java.util.ArrayList;

public class BottleDispenser {
    private static BottleDispenser bd = new BottleDispenser();
    private int bottles;
    private double money;
    private String receipt;
    private Object TextView;
    Bottle pullo;
    ArrayList<Object> Bottles;

    private BottleDispenser() {
        bottles = 0;
        money = 0;
        Bottles = new ArrayList<>(bottles);
        pullo = new Bottle("Pepsi", 0.3, 1.2);
        Bottles.add(pullo);
        pullo = new Bottle("Pepsi", 0.5, 1.8);
        Bottles.add(pullo);
        pullo = new Bottle("Pepsi", 1.5, 2.6);
        Bottles.add(pullo);
        pullo = new Bottle("Sprite", 0.3, 1.2);
        Bottles.add(pullo);
        pullo = new Bottle("Sprite", 0.5, 1.8);
        Bottles.add(pullo);
        pullo = new Bottle("Sprite", 1.5, 2.6);
        Bottles.add(pullo);
        pullo = new Bottle("Fanta", 0.3, 1.2);
        Bottles.add(pullo);
        pullo = new Bottle("Fanta", 0.5, 1.8);
        Bottles.add(pullo);
        pullo = new Bottle("Fanta", 1.5, 2.6);
        Bottles.add(pullo);
    }

    public static BottleDispenser getInstance(){
        return bd;
    }


    public void addMoney() {
        money += 1;
    }

    public String buyBottle(String brand, double size) {
        String r = null;
        if (Bottles.size() == 0){
            r = "Machine has ran out of bottles.";
        }
        else{
        for (int i = 0; i<Bottles.size(); i++){
            Bottle pullo = (Bottle)Bottles.get(i);
            if((pullo.getName().equals(brand)) && pullo.getSize() == size) {
                if (money < pullo.getPrice()) {
                    r = "Add money first!";
                } else{
                    money -= pullo.getPrice();
                    Bottles.remove(i);
                    r = "KACHUNK! " + pullo.getName() + " size of " + pullo.getSize() + " came out of the dispenser!";
                    makeReceipt("The receipt of last recent purchase "+pullo.getName()+" size of "+pullo.getSize()+"l and price "+pullo.getPrice()+"€.");
                }
            }
            if (r == null){
                r = "Sorry, the machine has ran out "+brand+" in size of "+size+"l.";
            }
            }
        }
        return r;
    }

    public String returnMoney()
    {

        String s ="Klink klink. Money came out! You got "+money+"€ back.";
        money = 0;
        return s;
    }


    public ArrayList getBottles(){
        return Bottles;
    }

    public double getMoney(){
        return (Math.round(money * 100.0)/ 100.0);
    }

    public void makeReceipt(String s){
        receipt = s;
    }

    public String getReceipt(){
        return receipt;
    }
}
