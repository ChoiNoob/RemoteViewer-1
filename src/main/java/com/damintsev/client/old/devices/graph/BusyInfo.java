package com.damintsev.client.old.devices.graph;

//import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Damintsev Andrey
 * Date: 18.08.13
 * Time: 22:18
 */
public class BusyInfo implements Serializable {

    private Long id;
    private Long deviceId;
    private Date date;
    private Long busy;
    private Long max;

    public BusyInfo() {

    }

    public BusyInfo(Date date, Long busy, Long max) {
        this.date = date;
        this.busy = busy;
        this.max = max;
    }

    public Long getBusy() {
        return busy;
    }

    public void setBusy(Long busy) {
        this.busy = busy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
