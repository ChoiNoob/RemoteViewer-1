package com.damintsev.servlet;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author Damintsev Andrey
 *         22.04.2014.
 */
//@Component
public class Security implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication);
        return null;
    }

    @Override
    public boolean supports(Class<? extends Object> aClass) {
        return false;
    }
}
