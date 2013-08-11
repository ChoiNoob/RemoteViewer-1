package com.damintsev.client.devices.enums;

/**
 * User: Damintsev Andrey
 * Date: 09.08.13
 * Time: 0:35
 */
public enum Status {
    INIT("yellow"), WORK("green"), ERROR("red");

    private String color;

    private Status(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
