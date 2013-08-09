package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:29
 */
public abstract class Device implements Serializable {

    public abstract void setId(int id);
    public abstract int getId();
    public abstract String getName();
    public abstract DeviceType getType();
    public abstract String getImage();
    public abstract Status getStatus();

    public Station getParentStation() {throw new UnsupportedOperationException();}
}