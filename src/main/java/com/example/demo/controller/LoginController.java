package com.example.demo.controller;

import com.example.demo.common.response.ServerResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping({"/","/index"})
    public String index(){
        return"index";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }

    @GetMapping("/login")
    public String login() throws Exception{
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public ServerResponse formLogin(HttpServletRequest request, String username,String password) throws Exception{
        System.out.println("HomeController.login()");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        System.out.println("UsernamePasswordToken:");
        System.out.println("hashCode:" + token.hashCode());
        System.out.println("Principal:" + token.getPrincipal());
        System.out.println("Credentials:" + String.valueOf((char[]) token.getCredentials()));
        System.out.println("host:" + token.getHost());
        System.out.println("Username:" + token.getUsername());
        System.out.println("Password:" + String.valueOf(token.getPassword()));
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
//            e.printStackTrace();
            System.out.println("login failed :" + e.getMessage());
        }
        return ServerResponse.success();
    }


}
