package com.damintsev.servlet;

import com.damintsev.server.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.Servlet;
import java.util.Arrays;

/**
 * @author Damintsev Andrey
 *         24.04.2014.
 */
@Controller
public class LoginServlet {

//    @Autowired
//    private Security security;

    @Autowired
    @Qualifier("authenticationRemeberProvider")
    private AuthenticationProvider authenticationProvider;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loginAttempt(Login login) {
        System.out.println(login.getLogin());
        System.out.println(login.getPassword());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

        GrantedAuthority authority = new GrantedAuthorityImpl("ROLE_USER");

        Authentication authentication = new RememberMeAuthenticationToken("springRocks", login.getLogin(), Arrays.asList(authority));

        Authentication authentication1 =  authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication1);



        return null;
    }
}
