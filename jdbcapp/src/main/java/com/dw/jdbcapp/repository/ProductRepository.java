package com.dw.jdbcapp.repository;

import com.dw.jdbcapp.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "select * from 제품";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("데이터베이스 연결 성공");

            while (resultSet.next()) {
                Product product = new Product();

                product.setProductId(resultSet.getInt("제품번호"));
                product.setProductName(resultSet.getString("제품명"));
                product.setPackageUnit(resultSet.getString("포장단위"));
                product.setUnitPrice(resultSet.getInt("단가"));
                product.setStock(resultSet.getInt("재고"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductUnitByProNum(int productNumber) {
        Product product = new Product();
        String query = "select * from 제품 where 제품번호 = ?";

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            System.out.println("데이터베이스 연결 성공");
            pstmt.setInt(1, productNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    product.setProductId(rs.getInt("제품번호"));
                    product.setProductName(rs.getString("제품명"));
                    product.setPackageUnit(rs.getString("포장단위"));
                    product.setUnitPrice(rs.getInt("단가"));
                    product.setStock(rs.getInt("재고"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // 제품테이블에 새로운 제품을 추가하는 API
    public Product saveProduct(Product product) {
        String query = "insert into 제품(제품번호, 제품명, 포장단위, 단가, 재고) "
                + "values (?, ?, ?, ?, ?)";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, product.getProductId());
            pstmt.setString(2, product.getProductName());
            pstmt.setString(3, product.getPackageUnit());
            pstmt.setDouble(4, product.getUnitPrice());
            pstmt.setInt(5, product.getStock());

            pstmt.executeUpdate();

            System.out.println("UPDATE 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // 제품테이블의 정보를 수정하는 API
    public Product updateProduct(Product product) {
        String query = "update 제품 set 제품명 = ?, 포장단위 = ?, 단가 = ?, 재고 = ? "
                + "where 제품번호 = ?";
        try(
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getPackageUnit());
            pstmt.setDouble(3, product.getUnitPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setInt(5, product.getProductId());

            pstmt.executeUpdate();
            System.out.println("UPDATE 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // 제품테이블의 정보를 삭제하는 API
    public String deleteProduct(String id) {
        String query = "delete from 제품 where 제품번호 = ?";
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);

            pstmt.executeUpdate();

            System.out.println("DELETE 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
