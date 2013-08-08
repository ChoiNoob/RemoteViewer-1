package com.damintsev.client.dao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 04.08.13
 * Time: 14:15
 */
@XmlRootElement
public class Station extends MyInter {

    @XmlElement
    private int id;
    @XmlElement private String name;
    @XmlElement  private String host;
    @XmlElement private String port;
    @XmlElement private String login;
    @XmlElement private String password;
    private  DeviceType deviceType = DeviceType.STATION;

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

    public DeviceType getType() {
        return deviceType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return "hipath";
    }
}
