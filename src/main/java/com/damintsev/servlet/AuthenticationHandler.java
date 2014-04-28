package com.damintsev.servlet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = authentication != null && !authentication.getPrincipal().equals("anonymousUser");
        return new ResponseEntity<>(authenticated, HttpStatus.OK);
    }
}
