package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.service.ServicePractice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPractice {
    @Autowired
    ServicePractice servicePractice;

    @GetMapping("/practice")
    public String practice() {
        if (servicePractice == null) {
            return "연습";
        } else {
            return servicePractice.practice();
        }
    }
}
