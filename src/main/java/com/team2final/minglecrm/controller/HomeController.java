package com.team2final.minglecrm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello, World!!";
    }

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
