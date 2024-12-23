package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.dto.OrderRequestDTO;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/find-all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(
                orderService.getAllOrders(), HttpStatus.OK);
    }

    // 2024.12.13 - Q2. 주문번호를 기준으로 주문 정보를 조회하는 API
    @GetMapping("/order")
    public ResponseEntity<Order> getOrderByOrderNumber2(@RequestParam String orderNumber) {
        return new ResponseEntity<>(
                orderService.getOrderById(orderNumber),
                HttpStatus.ACCEPTED);
    }

    // 2024.12.13 - Q2. 주문번호를 기준으로 주문 정보를 조회하는 API
    @GetMapping("/orders/{orderNumber}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderNumber) {
        return new ResponseEntity<>(
                orderService.getOrderById(orderNumber),
                HttpStatus.ACCEPTED);
    }

    // 2024.12.13 - Q4. 제품번호와 고객번호를 기준으로 해당 제품을 주문한 특정 고객의 주문 내역을 조회하는 API
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersOfCustomerByProductNumberAndCustomerId2
            (@RequestParam int productNumber, @RequestParam String customerId) {
        return new ResponseEntity<>(
                orderService.getOrderByIdAndCustomer(productNumber, customerId),
                HttpStatus.ACCEPTED);
    }

    // 2024.12.13 - Q4. 제품번호와 고객번호를 기준으로 해당 제품을 주문한 특정 고객의 주문 내역을 조회하는 API
    @GetMapping("/orders/{productNumber}/{customerId}")
    public ResponseEntity<List<Order>> getOrderByIdAndCustomer
            (@PathVariable int productNumber, @PathVariable String customerId) {
        return new ResponseEntity<>(
                orderService.getOrderByIdAndCustomer(productNumber, customerId),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/post/orders")
    public ResponseEntity<OrderRequestDTO> saveOrder(
            @RequestBody OrderRequestDTO orderRequestDTO) {
        return new ResponseEntity<>(
                orderService.saveOrder(orderRequestDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/orders/update")
    public ResponseEntity<String> updateOrderWithShippingDate(@RequestParam String id,@RequestParam String date) {
        return new ResponseEntity<>(
                orderService.updateOrderWithShippingDate(id, date), HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders/city/orderamount/{limit}")
    public ResponseEntity<List<Map<String,Integer>>> getTopCitiesByTotalOrderAmount(@PathVariable int limit) {
        return new ResponseEntity<>(
                orderService.getTopCitiesByTotalOrderAmount(limit), HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders/ordercount/year/{city}")
    public ResponseEntity<List<Map<String, Double>>> getOrderCountByYearForCity(@PathVariable String city) {
        return new ResponseEntity<>(
                orderService.getOrderCountByYearForCity(city), HttpStatus.ACCEPTED);
    }
}
