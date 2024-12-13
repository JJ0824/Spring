package com.dw.jdbcapp.repository;

import com.dw.jdbcapp.model.Order;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "select * from 주문";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("데이터베이스 연결 성공");

            while (resultSet.next()) {
                Order order = new Order();

                order.setOrderId(resultSet.getString("주문번호"));
                order.setCustomerId(resultSet.getString("고객번호"));
                order.setEmployeeId(resultSet.getString("사원번호"));
                order.setOrderDate(resultSet.getDate("주문일").toLocalDate());
                order.setRequestDate(resultSet.getDate("요청일").toLocalDate());
                order.setShippingDate(resultSet.getDate("발송일").toLocalDate());

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        Order order = new Order();
        String query = "select * from 주문 where 주문번호 = ?";

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            System.out.println("데이터베이스 연결 성공");
            pstmt.setString(1, orderNumber);
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    order.setOrderId(rs.getString("주문번호"));
                    order.setCustomerId(rs.getString("고객번호"));
                    order.setEmployeeId(rs.getString("사원번호"));
                    order.setOrderDate(rs.getDate("주문일").toLocalDate());
                    order.setRequestDate(rs.getDate("요청일").toLocalDate());
                    order.setShippingDate(rs.getDate("발송일").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
