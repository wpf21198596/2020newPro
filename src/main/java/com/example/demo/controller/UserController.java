package com.example.demo.controller;

import com.example.demo.common.response.ServerResponse;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/save")
    public ServerResponse save(UserInfo userInfo){
        UserInfo save = userInfoService.save(userInfo);
        if(save==null){
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }


}
