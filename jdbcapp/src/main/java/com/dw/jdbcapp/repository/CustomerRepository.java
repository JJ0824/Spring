package com.dw.jdbcapp.repository;

import com.dw.jdbcapp.model.Customer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        String query = "select * from 고객";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("데이터베이스 연결 성공");
            while (resultSet.next()) {
                Customer customer = new Customer();

                customer.setCustomerId(resultSet.getString("고객번호"));
                customer.setCompanyName(resultSet.getString("고객회사명"));
                customer.setContactName(resultSet.getString("담당자명"));
                customer.setContactTitle(resultSet.getString("담당자직위"));
                customer.setAddress(resultSet.getString("주소"));
                customer.setCity(resultSet.getString("도시"));
                customer.setRegion(resultSet.getString("지역"));
                customer.setPhone(resultSet.getString("전화번호"));
                customer.setMileage(resultSet.getInt("마일리지"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerByCustomerId(String id) {
        Customer customer = new Customer();
        String query = "select * from 고객 where 고객번호 = ?";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query);
             ) {
            System.out.println("데이터베이스 연결 성공");
            pstmt.setString(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customer.setCustomerId(rs.getString("고객번호"));
                    customer.setCompanyName(rs.getString("고객회사명"));
                    customer.setContactName(rs.getString("담당자명"));
                    customer.setContactTitle(rs.getString("담당자직위"));
                    customer.setAddress(rs.getString("주소"));
                    customer.setCity(rs.getString("도시"));
                    customer.setRegion(rs.getString("지역"));
                    customer.setPhone(rs.getString("전화번호"));
                    customer.setMileage(rs.getInt("마일리지"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getCustomersByProductNumberAndCustomerId(String productNumber, String customerId) {
        List<Customer> customers = new ArrayList<>();
        String query = "select 고객.*" +
                "from 고객 " +
                "inner join 주문 on 고객.고객번호 = 주문.고객번호 " +
                "inner join 주문세부 on 주문.주문번호 = 주문세부.주문번호 " +
                "where 주문세부.제품번호 = ? and 고객.고객번호 = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, productNumber);
            pstmt.setString(2, customerId);
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();

                    customer.setCustomerId(rs.getString("고객번호"));
                    customer.setCompanyName(rs.getString("고객회사명"));
                    customer.setContactName(rs.getString("담당자명"));
                    customer.setContactTitle(rs.getString("담당자직위"));
                    customer.setAddress(rs.getString("주소"));
                    customer.setCity(rs.getString("도시"));
                    customer.setRegion(rs.getString("지역"));
                    customer.setPhone(rs.getString("전화번호"));
                    customer.setMileage(rs.getInt("마일리지"));

                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
