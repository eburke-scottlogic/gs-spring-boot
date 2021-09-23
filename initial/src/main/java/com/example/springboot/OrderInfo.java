package com.example.springboot;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderInfo {

    @NotNull
    @DecimalMin("0.01")
    private float price;

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    @Size(min = 1)
    private String action;

    public OrderInfo(float price, int quantity, String action) {
        this.price = price;
        this.quantity = quantity;
        this.action = action;
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