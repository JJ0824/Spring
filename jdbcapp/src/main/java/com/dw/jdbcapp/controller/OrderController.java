package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/find-all-orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order")
    public Order getOrderByOrderNumber2(@RequestParam String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    @GetMapping("/order/{orderNumber}")
    public Order getOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    @GetMapping("/orders")
    public List<Order> getOrdersOfCustomerByProductNumberAndCustomerId2
            (@RequestParam String productNumber, @RequestParam String customerId) {
        return orderService.getOrdersOfCustomerByProductNumberAndCustomerId(productNumber, customerId);
    }

    @GetMapping("/orders/{productNumber}/{customerId}")
    public List<Order> getOrdersOfCustomerByProductNumberAndCustomerId
            (@PathVariable String productNumber, @PathVariable String customerId) {
        return orderService.getOrdersOfCustomerByProductNumberAndCustomerId(productNumber, customerId);
    }
}
