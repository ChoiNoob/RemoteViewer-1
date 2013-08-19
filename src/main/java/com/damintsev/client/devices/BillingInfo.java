package com.damintsev.client.devices;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 14.08.13 15:15
 */
public class BillingInfo implements Serializable {

    private Long id;
    private String number;
    private String numberFrom;
    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getNumberFrom() {
        return numberFrom;
    }

    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom;
    }
}
