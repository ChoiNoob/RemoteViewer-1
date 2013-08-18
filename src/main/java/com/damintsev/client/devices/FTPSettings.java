package com.damintsev.client.devices;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 14.08.13
 * Time: 23:35
 */
@XmlRootElement
public class FTPSettings implements Serializable {

    private String host;
    private String port;
    private String login;
    private String password;
    private String dir;

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
}
