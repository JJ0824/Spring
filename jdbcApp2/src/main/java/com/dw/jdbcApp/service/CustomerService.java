package com.dw.jdbcApp.service;

import com.dw.jdbcApp.model.Customer;
import com.dw.jdbcApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }
}
