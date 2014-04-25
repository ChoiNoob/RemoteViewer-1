package com.damintsev.servlet;

import com.damintsev.server.entity.Login;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Damintsev Andrey
 *         22.04.2014.
 */
//@Component
public class Security implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println(authentication);
        System.err.println("ASDDDDDDDASDASDASDASDASDASDASDASDASJDHKASDJASJDASJDJSSDJASDJASFLHFABSFashfJSHF:");
        return null;
    }

    @Override
    public boolean supports(Class<? extends Object> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void authenticate(Login login) {
    }
}
