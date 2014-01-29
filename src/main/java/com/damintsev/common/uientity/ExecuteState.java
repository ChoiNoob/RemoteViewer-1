package com.damintsev.common.uientity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:32
 */
public enum ExecuteState implements IsSerializable {

    INIT("gray"), WORK("green"), ERROR("red"), WARNING("yellow");//todo

    private String color;

    private ExecuteState(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
