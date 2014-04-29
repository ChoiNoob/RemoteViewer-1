package com.damintsev.servlet;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.Access;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author damintsev
 * 4/29/2014
 */
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private final static Logger logger = Logger.getLogger(AccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        logger.debug("Access denied. Returning 401");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
