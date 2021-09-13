package com.example.springboot;

public class Order {
    private String account;
    private float price;
    private int quantity;
    private String action;

    public Order(String account, float price, int quantity, String action){
        this.account=account;
        this.price=price;
        this.quantity=quantity;
        this.action=action;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
