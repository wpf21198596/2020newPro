package com.example.demo.controller;

import com.example.demo.common.response.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/stamp")
    public ServerResponse stamp(String keyWord){

        return null;
    }


}
