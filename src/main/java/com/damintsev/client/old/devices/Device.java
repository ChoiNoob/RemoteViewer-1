package com.damintsev.client.old.devices;

import com.damintsev.client.old.devices.enums.DeviceType;
import com.damintsev.client.old.devices.enums.Status;
import com.damintsev.common.pojo.Station;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:29
 */
public abstract class Device implements Serializable {

    private Response response;

    public abstract void setId(Long id);
    public abstract Long getId();
    public abstract String getName();
    public abstract DeviceType getDeviceType();
    public abstract String getImage();
    public abstract Status getStatus();
    public abstract void setStatus(Status status);

    public abstract Station getStation();

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
