package com.damintsev.server.entity;

/**
 * @author Damintsev Andrey
 *         24.04.2014.
 */
public class Login {

    private String login;
    private String password;
    private Boolean rememberMe;

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

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
