package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.OrderRequestDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.model.OrderDetail;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.beans.Transient;
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

    // @Transactional은 선언된 메서드 수행 도중 예외가 발생하면 이미 수행되었던 동작을
    // 모두 롤백(rollback=원상복귀)시키도록 명령하는 어노테이션임
    // 주문세부의 특정 제품의 재고가 부족해서 예외가 발생하면 전체 주문, 주문세부의
    // 저장되었던 내용들은 모두 취소되고 롤백됨!!
    @Transactional
    public OrderRequestDTO saveOrder(OrderRequestDTO orderRequestDTO) {
        // 1. DTO에서 주문정보를 꺼내 주문 테이블에 insert
        orderRepository.saveOrder(orderRequestDTO.toOrder());
        // 2. DTO에서 주문세부정보를 꺼내 주문세부테이블에 insert. 반복문 필요
        for (OrderDetail data : orderRequestDTO.getOrderDetails()) {
            // 12. 20 - Q7 주문입력 API에서 아래 예외를 추가하시오
            // 제품테이블의 재고보다 많은 양을 주문하는 경우 예외 발생
            Product product = productRepository.getProductById(data.getProductId());

            if (data.getOrderQuantity()>product.getStock()) {
                throw new InvalidRequestException(
                        "요청하신 수량은 현재 재고를 초과합니다: " +
                        product.getProductId() + ", 현재 재고 " +
                        product.getStock()
                );
            }
            orderDetailRepository.saveOrderDetail(data);
        }
        return orderRequestDTO;
    }

    // 12. 20 - Q4. 주문번호와 발송일을 매개변수로 해당 주문의 발송일을 수정하는 API
    public String updateOrderWithShippingDate(String id, String date) {
        return orderRepository.updateOrderWithShippingDate(id, date);
    }

    // 12. 20 - Q5. 도시별로 주문금액합 결과를 내림차순 정렬하여 조회하는 API
    public List<Map<String,Integer>> getTopCitiesByTotalOrderAmount(int limit) {
        List<Map<String, Object>> temp = orderRepository.getTopCitiesByTotalOrderAmount(limit);
        List<Map<String, Integer>> orderCountCities = new ArrayList<>();
        for (Map<String, Object> objectMap : temp) {
            Map<String, Integer> orderCountCity = new HashMap<>();

            orderCountCity.put(objectMap.get("도시").toString(),
                    Integer.valueOf(objectMap.get("주문금액합").toString()));

            orderCountCities.add(orderCountCity);
        }
        return orderCountCities;
    }

    // 12. 20 - Q6 도시를 매개변수로 해당 도시의 년도별 주문건수를 조회하는 API
    public List<Map<String, Double>> getOrderCountByYearForCity(String city) {
        List<Map<String, Object>> temp = orderRepository.getOrderCountByYearForCity(city);
        List<Map<String, Double>> orderCounts = new ArrayList<>();
        for (Map<String, Object> objectMap : temp) {
            Map<String, Double> orderCount = new HashMap<>();

            orderCount.put(objectMap.get("주문년도").toString(),
                    Double.valueOf(objectMap.get("주문건수").toString()));

            orderCounts.add(orderCount);
        }
        return orderCounts;
    }
}