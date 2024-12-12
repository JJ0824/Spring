package com.dw.jdbcApp.controller;

import com.dw.jdbcApp.service.ServicePractice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Practice {
    @Autowired
    ServicePractice servicePractice;

    @GetMapping("/practice")
    public String practice() {
        return servicePractice.practice();
    }
}
