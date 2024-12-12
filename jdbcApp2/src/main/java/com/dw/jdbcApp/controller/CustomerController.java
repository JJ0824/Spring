package com.dw.jdbcApp.controller;

import com.dw.jdbcApp.model.Customer;
import com.dw.jdbcApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/find-all-customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
