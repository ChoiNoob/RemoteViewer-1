package com.damintsev.servlet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Damintsev Andrey
 *         28.04.2014.
 *         Check if user is Authenticated (I dont want show login page to user), and returns request.getContextPath()
 *         of servlet
 */
@Controller
public class AuthenticationHandler {

    /**
     * @return context path where applications is deployed
     */
    @RequestMapping("authenticated")
    @ResponseBody
    public ResponseEntity<String> isAuthenticated(HttpServletRequest request) {
        Authentication authenticationInfo = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationInfo != null && authenticationInfo instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(request.getContextPath(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(request.getContextPath(), HttpStatus.OK);
    }
}
