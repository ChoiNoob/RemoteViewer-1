package com.damintsev.server.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

/**
 * Author damintsev
 * 4/25/2014
 */
@Entity
@Table(name = "USER_DETAILS")
public class UserDetailsImpl implements UserDetails {

    @Id
    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    private String authorities;

    public UserDetailsImpl(){
    }

    public UserDetailsImpl(String username, String password, String authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
//        Arrays.asList(authorities.split(" ")).stream().forEach((authority) -> {
//            authoritiesList.add(new SimpleGrantedAuthority(authority));
//        });
        for(String authority : authorities.split(" ")) {
            authoritiesList.add(new SimpleGrantedAuthority(authority.trim()));
        }
        return authoritiesList;
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