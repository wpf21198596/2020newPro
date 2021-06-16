package com.example.system.controller;

import com.example.demo.common.response.ServerResponse;
import com.example.system.entity.UserDo;
import com.example.system.uitl.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
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
    public ServerResponse login(){
        UserDo user = ShiroUtil.getUser();
        if(user==null)return ServerResponse.error();
        return ServerResponse.success();
    }

    @ResponseBody
    @PostMapping("/login")
    public ServerResponse formLogin(HttpServletRequest request, String username,String password) throws Exception{
        System.out.println("HomeController.login()");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
//            e.printStackTrace();
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }


}
