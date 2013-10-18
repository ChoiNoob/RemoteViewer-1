package com.damintsev.client.old.devices;

import com.damintsev.client.old.devices.enums.DeviceType;
import com.damintsev.client.old.devices.enums.Status;
import com.damintsev.client.old.devices.graph.BusyInfo;
import com.damintsev.common.pojo.Station;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:45
 */
public class CommonDevice extends Device {

    private Long id;
    private String name;
    private String query;
    private String queryBusy;
    private String imageName = "cloud";
    private Status status;
    private Station station;
    private DeviceType deviceType;
    private String comment;
    private BusyInfo busyInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getImage() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryBusy() {
        return queryBusy;
    }

    public void setQueryBusy(String queryBusy) {
        this.queryBusy = queryBusy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BusyInfo getBusyInfo() {
        return busyInfo;
    }

    public void setBusyInfo(BusyInfo busyInfo) {
        this.busyInfo = busyInfo;
    }
}
