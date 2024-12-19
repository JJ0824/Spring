package com.dw.jdbcapp.service;

import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    @Qualifier("orderTemplateRepository")
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Order getOrderById(String orderNumber) {
        return orderRepository.getOrderById(orderNumber);
    }

    public List<Order> getOrderByIdAndCustomer(int productNumber, String customerId) {
        List<Order> orderList = orderRepository.getOrderByIdAndCustomer(productNumber, customerId);
        if(orderList.isEmpty()) {
            throw new InvalidRequestException("주문정보가 확인되지 않습니다 : "
                    + productNumber + ", " + customerId);
        }else {
            return orderList;
        }
    }
}