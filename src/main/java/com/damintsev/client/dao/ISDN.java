package com.damintsev.client.dao;

/**
 * Created by adamintsev
 * Date: 08.08.13 13:45
 */
public class ISDN extends MyInter {

    private int id;
    private String name;
    private String query;
    private String regExp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return DeviceType.ISDN;
    }

    public String getImage() {
        return "cloud_130";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
