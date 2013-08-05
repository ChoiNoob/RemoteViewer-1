package com.damintsev.client.dao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

/**
 * User: Damintsev Andrey
 * Date: 04.08.13
 * Time: 14:15
 */
public class Station implements Item {

    public String host;
    public String port;
    public String login;
    public String password;

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
}

//class StationEditor  implements Editor<Station> {
//
//    interface Driver extends SimpleBeanEditorDriver<Station, StationEditor> {
//    }
//
//    FieldLabel host;
//
//    Driver driver = GWT.create(Driver.class);
//
//    public StationEditor() {
//        host = new FieldLabel();
//
//    }
//}
