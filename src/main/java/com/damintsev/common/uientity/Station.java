package com.damintsev.common.uientity;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.visitor.Visitor;

//import javax.persistence.*;

/**
 * User: Damintsev Andrey
 * Date: 04.08.13
 * Time: 14:15
 */
//@Entity
//@org.hibernate.annotations.Entity
public class Station extends Item {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "station_id")
    private Long id;
//    @Column
    private String name;
//    @Column
    private String host;
//    @Column
    private String port;
//    @Column
    private String login;
//    @Column
    private String password;
//    @Column
    private String comment;
//    @Transient
    private Long imageId;
//    @Column
    private Boolean allowStatistics;
    private Integer delay;

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

    @Override
    public TaskType getType() {
        return TaskType.STATION;
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

    public Long getImageId() {
        if(imageId == null || imageId == 0) imageId = DefaultImages.STATION.getValue();
        return imageId;
    }

    @Override
    public Station getStation() {
        return this;
    }

    @Override
    public String getParentId() {
        return null;
    }

    public void setImage(Long imageId) {
        this.imageId = imageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAllowStatistics() {
        return allowStatistics;
    }

    public void setAllowStatistics(Boolean allowStatistics) {
        this.allowStatistics = allowStatistics;
    }

    @Override
    public String getStringId() {
        return "s" + getId();
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
