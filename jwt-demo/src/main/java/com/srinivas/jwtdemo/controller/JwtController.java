package com.srinivas.jwtdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class JwtController {

    @RequestMapping("/world")
    public String helloWorld(){
        return "Hello, World";
    }
}
