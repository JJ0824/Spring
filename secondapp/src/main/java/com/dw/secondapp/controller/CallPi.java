package com.dw.secondapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallPi {
    @GetMapping("/callpi")
    public String Pi() {
        return (" "+Math.PI);
    }
}
