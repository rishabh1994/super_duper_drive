package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
@Slf4j
@AllArgsConstructor
public class ResultController {

    @GetMapping()
    public String getResultPage() {
        return "result";
    }
}
