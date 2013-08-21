package com.damintsev.client.devices;


import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 20.08.13 12:43
 */
public class BillingStats implements Serializable {

    private String number;
    private String name;
    private Long quantity;

    public BillingStats(){

    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long increaseQuantity() {
        return ++quantity;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
