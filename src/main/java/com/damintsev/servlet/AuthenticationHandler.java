package com.damintsev.servlet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Security;

/**
 * @author Damintsev Andrey
 *         28.04.2014.
 */
@Controller
public class AuthenticationHandler {

    @RequestMapping("authenticated")
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated() {
        Authentication authenticationInfo = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = true;
        if(authenticationInfo != null && authenticationInfo instanceof AnonymousAuthenticationToken) {
            authenticated = false;
        }
//        boolean authenticated = authenticationInfo != null && !authenticationInfo.getPrincipal().equals("anonymousUser");
        if(authenticated)
            return new ResponseEntity<>(authenticated, HttpStatus.OK);
        else return new ResponseEntity<>(authenticated, HttpStatus.FORBIDDEN);
    }
}
