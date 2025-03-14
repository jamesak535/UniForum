package com.uniforum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/api/test")
    public String testEndpoint() {
        return "UniForum backend is running successfully!";
    }
}