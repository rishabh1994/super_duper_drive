package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @GetMapping
    public String getLoginPage() {
        log.warn("--------GET REQUEST LOGIN PAGE--------");
        log.warn("--------GET REQUEST LOGIN PAGE--------");
        return "login";
    }
}
