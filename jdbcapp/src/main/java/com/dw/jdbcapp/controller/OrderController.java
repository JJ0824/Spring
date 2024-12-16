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

    // 2024.12.13 - Q2. 주문번호를 기준으로 주문 정보를 조회하는 API
    @GetMapping("/order")
    public Order getOrderByOrderNumber2(@RequestParam String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    // 2024.12.13 - Q2. 주문번호를 기준으로 주문 정보를 조회하는 API
    @GetMapping("/order/{orderNumber}")
    public Order getOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    // 2024.12.13 - Q4. 제품번호와 고객번호를 기준으로 해당 제품을 주문한 특정 고객의 주문 내역을 조회하는 API
    @GetMapping("/orders")
    public List<Order> getOrdersOfCustomerByProductNumberAndCustomerId2
            (@RequestParam String productNumber, @RequestParam String customerId) {
        return orderService.getOrdersOfCustomerByProductNumberAndCustomerId(productNumber, customerId);
    }

    // 2024.12.13 - Q4. 제품번호와 고객번호를 기준으로 해당 제품을 주문한 특정 고객의 주문 내역을 조회하는 API
    @GetMapping("/orders/{productNumber}/{customerId}")
    public List<Order> getOrdersOfCustomerByProductNumberAndCustomerId
            (@PathVariable String productNumber, @PathVariable String customerId) {
        return orderService.getOrdersOfCustomerByProductNumberAndCustomerId(productNumber, customerId);
    }
}
