package com.damintsev.server.v2.machine.state;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:32
 */
public enum  State {

    INIT("gray"), WORK("green"), ERROR("red"), WARNING("yellow"), UNKNOWN("xyu");//todo

    private String color;

    private State(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
