package com.damintsev.client.devices.enums;

/**
 * User: Damintsev Andrey
 * Date: 06.08.13
 * Time: 1:45
 */
public enum DeviceType {
    STATION("STATION"), ISDN("ISDN"), IP("IP");

    private String name;

    private DeviceType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
