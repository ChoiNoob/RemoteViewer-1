package com.damintsev.servlet;


import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Author damintsev
 * 4/25/2014
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = -6509897037222767090L;

    private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    private String password;
    private String username;

    public UserDetailsImpl(String username, String password, Collection<GrantedAuthority> authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Collection getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}