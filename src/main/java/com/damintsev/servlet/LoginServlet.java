package com.damintsev.servlet;

import com.damintsev.server.entity.Login;
import com.login.login.CustomAth;
import com.login.login.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Damintsev Andrey
 *         24.04.2014.
 */
@Controller
public class LoginServlet {

    @Autowired
    CustomAth customAth;

    @Autowired
    private Security security;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loginAttempt(Login login, HttpServletRequest request, HttpServletResponse response) {
//        authenticationManager.authenticate()
        System.out.println(login.getLogin());
        System.out.println(login.getPassword());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

//        GrantedAuthority authority = new GrantedAuthorityImpl("ROLE_USER");

//        Authentication authentication = new RememberMeAuthenticationToken("springRocks", login.getLogin(), Arrays.asList(authority));


        Authentication authentication1 = customAth.login(login.getLogin(), login.getPassword(), login.getRememberMe());
        security.remeberMe(request, response, authentication1);
//        Authentication authentication1 =  authenticationManager.authenticate(authentication);


//        Authentication authentication1 =  authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication1);




        return null;
    }
}
