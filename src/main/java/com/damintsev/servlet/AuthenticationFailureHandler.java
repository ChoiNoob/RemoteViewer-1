package com.damintsev.servlet;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author damintsev
 * 4/29/2014
 */
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private final static Logger logger = Logger.getLogger(AuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.debug("Authentication failed " + e.getMessage(), e);
        if(e instanceof BadCredentialsException) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
