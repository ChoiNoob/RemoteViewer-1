package com.damintsev.common.uientity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 22:43
 */
public enum TaskType implements IsSerializable{

    STATION("STATION"), ISDN("ISDN"), IP("IP"), LABEL("label");

    private String name;

    private TaskType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
