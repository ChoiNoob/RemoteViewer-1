package com.damintsev.server.db.xmldao;

import com.damintsev.client.devices.enums.DeviceType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by adamintsev
 * Date: 08.08.13 16:43
 */
@XmlRootElement
public class XMLItem {

    private Long id;
    private String name;
    private String query;
    private String regExp;
    private String host;
    private String port;
    private String login;
    private String password;
    private String comment;
    private String deviceType;
    private Long stationId;

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }
    @XmlElement
    public void setQuery(String query) {
        this.query = query;
    }

    public String getRegExp() {
        return regExp;
    }
    @XmlElement
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    @XmlElement
    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
    @XmlElement
    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    @XmlElement
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
    @XmlElement
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setStationId(Long id) {
        this.stationId = id;
    }

    public Long getStationId() {
        return stationId;
    }
}
