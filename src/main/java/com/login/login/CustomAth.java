//package com.login.login;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
///**
// * @author Damintsev Andrey
// *         26.04.2014.
// */
//@Component
//public class CustomAth {
//
//    private final static Logger log = Logger.getLogger(CustomAth.class);
//
////    @Autowired
////    private TokenBasedRememberMeServices rememberMeServices;
//
//    @Autowired
//    @Qualifier("authenticationManager")
//    private AuthenticationManager authenticationManager;
//
//    public Authentication login(String login, String password, boolean rememberMe) {
//        Authentication authentication = null;
//        try {
//            log.info("Trying to authenticate user {" + login + "}");
//            Authentication aut = new UsernamePasswordAuthenticationToken(login, password);
////            Authentication aut = new RememberMeAuthenticationToken("springRocks", login, new ArrayList<GrantedAuthority>());
//            authentication = authenticationManager.authenticate(aut);
//        } catch (AuthenticationException ex) {
//            log.warn("User is not authenticated. Can't authenticate ", ex);
//            throw ex;
//        }
////        boolean isAuthenticated = isAuthenticated(authentication);
////        if (true) {
////            log.info("User {" + login + "} successfully authenticated!");
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////            if (true)
////                addRememberMeCookie(authentication);
////        }
//        return authentication;
//    }
//
//
////    @Autowired
////    private HttpServletRequest request;
////    @Autowired
////    private HttpServletResponse httpServletResponse;
////
//    public void addRememberMeCookie(Authentication authentication) {
//        log.info("Adding remember me cookies");
////        rememberMeServices.setAlwaysRemember(true);
////        rememberMeServices.loginSuccess(request, httpServletResponse, authentication);
//    }
//
//    private boolean isAuthenticated(Authentication authentication) {
//        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
//    }
//}
