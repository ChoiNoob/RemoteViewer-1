package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:45
 */
public class ISDN extends Device {

    private Long id;
    private String name;
    private String query;
    private String regExp;
    private String imageName = "cloud";
    private Status status;
    private Station parentStation;
    private DeviceType type = DeviceType.ISDN;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
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

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
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

    public Station getParentStation() {
        return parentStation;
    }

    public void setParentStation(Station parentStation) {
        this.parentStation = parentStation;
    }
}
