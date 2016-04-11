package com.arthur.controllers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 1/6/2016.
 */
@Controller
@Scope("prototype")
public class LoginController {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String userLogin(HttpServletRequest request,
                        HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String authentication(HttpServletRequest request,
                        HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                request.getParameter("username"),
                request.getParameter("password"));
                token.setRememberMe(true);
        try{
            currentUser.login(token);
        }catch(AuthenticationException e){
            System.out.println("login failure!");
        }

        return "redirect:ui/users";
    }
}
