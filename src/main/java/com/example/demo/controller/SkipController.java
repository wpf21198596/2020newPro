package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skip")
public class SkipController {

    @RequestMapping("/toDemo1")
    public String toDemo1(){
        return "/test/test1.html";
    }

}
