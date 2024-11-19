package com.dragand.spring_tutorial.ca2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/auth")
    public String userIndex(){
        return "auth";
    }

    @GetMapping("/messages")
    public String customerIndex(){
        return "messages";
    }

    @GetMapping("/logout")
    public String employeeIndex(){
        return "logout";
    }

}
