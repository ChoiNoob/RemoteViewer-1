package com.damintsev.client.old.devices;

import com.damintsev.common.pojo.Station;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 14.08.13
 * Time: 23:35
 */
@XmlRootElement
@Entity
@org.hibernate.annotations.Entity
public class FTPSettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long id;

    @JoinColumn(name = "station_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Station station;

    @Transient
    private Long stationId;
    
    @Column
    @XmlElement
    private String host;
    @Column
    private String port;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String dir;


    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getDir() {
        return dir;
    }

    @XmlElement
    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    @XmlElement
    public void setHost(String host) {
        this.host = host;
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    @XmlElement
    public void setPort(String port) {
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStationId() {
        return stationId;
    }
     @XmlElement
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
