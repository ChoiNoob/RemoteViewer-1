package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Damintsev Andrey
 * Date: 04.08.13
 * Time: 14:15
 */
@Entity
@org.hibernate.annotations.Entity
public class Station extends Device {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String host;
    @Column
    private String port;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private Status status;
    @Column
    private String comment;
    @Transient
    private String imageName = "hipath";
    @Column
    private DeviceType deviceType = DeviceType.STATION;
    @Column
    private Boolean allowStatistics;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return imageName;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Station getStation() {
        return this;
    }

    public void setImage(String imageName) {
        this.imageName = imageName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Boolean getAllowStatistics() {
        return allowStatistics;
    }

    public void setAllowStatistics(Boolean allowStatistics) {
        this.allowStatistics = allowStatistics;
    }
}
