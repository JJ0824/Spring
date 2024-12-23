package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.OrderRequestDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.model.OrderDetail;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    @Qualifier("orderTemplateRepository")
    OrderRepository orderRepository;

    @Autowired
    @Qualifier("productTemplateRepository")
    ProductRepository productRepository;

    @Autowired
    @Qualifier("orderDetailTemplateRepository")
    OrderDetailRepository orderDetailRepository;

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

    public OrderRequestDTO saveOrder(OrderRequestDTO orderRequestDTO) {
        // 1. DTO에서 주문정보를 꺼내 주문 테이블에 insert
        orderRepository.saveOrder(orderRequestDTO.toOrder());
        // 2. DTO에서 주문세부정보를 꺼내 주문세부테이블에 insert. 반복문 필요
        for (OrderDetail data : orderRequestDTO.getOrderDetails()) {
            Product product = productRepository.getProductById(data.getProductId());

            if (data.getOrderQuantity()>product.getStock()) {
                throw new InvalidRequestException(
                        "요청하신 수량은 현재 재고를 초과합니다: " + product.getStock()
                );
            }else { //
                orderDetailRepository.saveOrderDetail(data);
            }
        }
        return orderRequestDTO;
    }

    public String updateOrderWithShippingDate(String id, String date) {
        return orderRepository.updateOrderWithShippingDate(id, date);
    }

    public List<Map<String,Integer>> getTopCitiesByTotalOrderAmount(int limit) {
        List<Map<String, Object>> temp = orderRepository.getTopCitiesByTotalOrderAmount(limit);
        List<Map<String, Integer>> orderCountCities = new ArrayList<>();
        for (Map<String, Object> objectMap : temp) {
            Map<String, Integer> orderCountCity = new HashMap<>();

            orderCountCity.put("주문금액합", Integer.valueOf(objectMap.get("주문금액합").toString()));

            orderCountCities.add(orderCountCity);
        }
        return orderCountCities;
    }

    public List<Map<String, Double>> getOrderCountByYearForCity(String city) {
        List<Map<String, Object>> temp = orderRepository.getOrderCountByYearForCity(city);
        List<Map<String, Double>> orderCounts = new ArrayList<>();
        for (Map<String, Object> objectMap : temp) {
            Map<String, Double> orderCount = new HashMap<>();
            orderCount.put("주문년도", Double.valueOf(objectMap.get("주문년도").toString()));
            orderCount.put("주문건수", Double.valueOf(objectMap.get("주문건수").toString()));

            orderCounts.add(orderCount);
        }
        return orderCounts;
    }
}