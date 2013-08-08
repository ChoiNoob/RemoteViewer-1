package com.damintsev.server.db.xmldao;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by adamintsev
 * Date: 08.08.13 16:43
 */
@XmlRootElement
public class XMLItem {

    private int id;
    private String name;
    private String query;
    private String regExp;
    private String type;
    private String host;
    private String port;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
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

    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
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
}
