package com.damintsev.servlet;

import com.damintsev.server.entity.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Damintsev Andrey
 *         24.04.2014.
 */
@Controller
public class LoginServlet {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loginAttempt(Login login) {
        System.out.println(login.getLogin());
        System.out.println(login.getPassword());
        return null;
    }
}
