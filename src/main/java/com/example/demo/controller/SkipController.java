package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skip/user")
public class SkipController {

//    @RequestMapping("/addUser")
//    public String toDemo1(){
//        return "/test/test1.html";
//    }

    @RequestMapping("/addUser")
    public String addUser(){
        return "system/user/add";
    }

    @RequestMapping("/noPre")
    public String skipNoPre(){
        return "403";
    }

}
