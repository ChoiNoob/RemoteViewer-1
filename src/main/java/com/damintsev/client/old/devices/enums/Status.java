package com.damintsev.client.old.devices.enums;

/**
 * User: Damintsev Andrey
 * Date: 09.08.13
 * Time: 0:35
 */
public enum Status {
    INIT("gray"), WORK("green"), ERROR("red"), WARNING("yellow");

    private String color;

    private Status(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
