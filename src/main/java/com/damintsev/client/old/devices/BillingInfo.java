package com.damintsev.client.old.devices;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adamintsev
 * Date: 14.08.13 15:15
 */
public class BillingInfo implements Serializable {

    private Long id;
    private Date date;
    private String number;
    private String numberShort;
    private String numberFrom;
    private Long quantity;
    private String callDuration;
    private Long trunkNumber;
    private String name;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumberShort() {
        return numberShort;
    }

    public void setNumberShort(String numberShort) {
        this.numberShort = numberShort;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public void setTrunkNumber(Long trunkNumber) {
        this.trunkNumber = trunkNumber;
    }

    public Long getTrunkNumber() {
        return trunkNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
