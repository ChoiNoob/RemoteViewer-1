//package com.login.login;
//
//import com.damintsev.server.entity.Login;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.RememberMeAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
//import org.springframework.security.web.authentication.RememberMeServices;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Arrays;
//
///**
// * @author Damintsev Andrey
// *         22.04.2014.
// */
//@Component
//public class Security {
//
//    @Autowired
//    private RememberMeServices rememberMeServices;
//
//    public void remeberMe(HttpServletRequest request, HttpServletResponse response, Authentication authentication1) {
////        rememberMeServices.setAlwaysRemember(true);
//        rememberMeServices.loginSuccess(request, response, authentication1);
//    }
//}
