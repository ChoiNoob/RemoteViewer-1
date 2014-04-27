package com.damintsev.servlet;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author damintsev
 * 4/25/2014
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        logger.debug("loadUserByUsername=" + username);
        GrantedAuthority authority = new GrantedAuthorityImpl("ROLE_USER");
        UserDetails userDetails = new UserDetailsImpl(username, "1234", Arrays.asList(authority));
        return userDetails;
    }
}
