package com.damintsev.client.dao;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:29
 */
public abstract class MyInter implements Serializable {

    public abstract String getName();

    public abstract DeviceType getType();

    public abstract String getImage();

    public abstract void setId(int id);
    public abstract int  getId();

}
