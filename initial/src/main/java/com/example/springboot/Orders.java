package com.example.springboot;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table
public class Orders {

    @Id @GeneratedValue long id;
    @Column
    @Size(min=1)
    private String account;

    @Column
    @DecimalMin("0.01")
    private float price;

    @Column
    @Min(1)
    private int quantity;

    @Column
    @Size(min=1)
    private String action;

    public Orders () {}

    public Orders(String account, float price, int quantity, String action){
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

    @Override
    public String toString() {
        return "Order{" +
                "account='" + account + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", action='" + action + '\'' +
                '}';
    }
}
