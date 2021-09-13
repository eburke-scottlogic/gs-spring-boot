package com.example.springboot;

public class Trade {
    private String account1;
    private String account2;
    private float price;
    private int quantity;
    private String action;

    public Trade(String account1, String account2, float price, int quantity, String action){
        this.account1=account1;
        this.account2=account2;
        this.price=price;
        this.quantity=quantity;
        this.action=action;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public String getAccount2() {
        return account2;
    }

    public void setAccount2(String account2) {
        this.account2 = account2;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
