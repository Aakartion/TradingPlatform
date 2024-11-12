package edu.miu.TradingPlatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public String home(){
        return "Welcome to Trading application";
    }

    @GetMapping("/api")
    public String secure(){
        return "Welcome to secure Trading application";
    }
}
