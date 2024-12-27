package com.dw.jdbcapp.repository.jdbc;

import com.dw.jdbcapp.model.Customer;
import com.dw.jdbcapp.repository.iface.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "select * from 고객";
        try (
                Connection connection = DriverManager.getConnection(
                        URL, USER, PASSWORD);
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

    // 과제 4-1 전체 평균마일리지보다 큰 마일리지를 가진 고객들을 조회하는 API
    @Override
    public List<Customer> getCustomersWithHighMileThanAvg() {
        List<Customer> customers = new ArrayList<>();
        String query = "select * from 고객 " +
                "where 마일리지 > (select avg(마일리지) from 고객)";
        try (
                Connection conn = DriverManager.getConnection(
                        URL, USER, PASSWORD);
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query)) {
            while(rs.next()) {
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // 과제 4-2 마일리지등급을 매개변수로 해당 마일리지 등급을 가진 고객들을 조회하는 API
    @Override
    public List<Customer> getCustomersByMileageGrade(String grade) {
        List<Customer> customers = new ArrayList<>();
        String query = "select * from 고객 " +
                "join 마일리지등급 on 고객.마일리지 >= 마일리지등급.하한마일리지 " +
                "and 고객.마일리지 < 마일리지등급.상한마일리지 " +
                "where 마일리지등급.등급명 = ? " +
                "order by 고객.마일리지 desc";
        try (
            Connection conn = DriverManager.getConnection(
                    URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query)) {
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}