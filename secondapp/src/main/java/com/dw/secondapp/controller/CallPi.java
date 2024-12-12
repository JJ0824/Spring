package com.dw.secondapp.controller;

import com.dw.secondapp.service.CallPiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallPi {
    @Autowired
    CallPiService callPiService;

    @GetMapping("/pi")
    public String Pi() {
        if (callPiService.Pi()==null) {
            return " "+Pi();
        } else {
            return callPiService.Pi();
        }
    }
}
