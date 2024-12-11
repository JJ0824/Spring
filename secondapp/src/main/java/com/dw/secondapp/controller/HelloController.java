package com.dw.secondapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/makenew")
    public String MakeNew() {
        return "New";
    }
}
