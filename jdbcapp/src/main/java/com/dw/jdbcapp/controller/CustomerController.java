package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Customer;
import com.dw.jdbcapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/find-all-customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer")
    public Customer getCustomerById(@RequestParam String id) {
        return customerService.getCustomerByCustomerId(id);
    }

    @GetMapping("/api/orders")
    public List<Customer> getCustomersByProductNumberAndCustomerId2
            (@RequestParam String productNumber, @RequestParam String customerId) {
        return customerService.getCustomersByProductNumberAndCustomerId(productNumber, customerId);
    }

    @GetMapping("/api/orders/{productNumber}/{customerId}")
    public List<Customer> getCustomersByProductNumberAndCustomerId
            (@PathVariable String productNumber, @PathVariable String customerId) {
        return customerService.getCustomersByProductNumberAndCustomerId(productNumber, customerId);
    }
}
