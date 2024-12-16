package com.dw.jdbcapp.service;

import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductUnit(int productNumber) {
        return productRepository.getProductUnitByProNum(productNumber);
    }

    // 1. 제품테이블에 새로운 제품 1개를 추가하는 API
    public Product saveProduct(Product product) {
        return productRepository.saveProduct(product);
    }

    // 2. 제품테이블에 여러 제품을 추가하는 API
    public List<Product> saveProductList(List<Product> productList) {
        for (Product data : productList) {
            productRepository.saveProduct(data);
        }
        return productList;
    }

    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public String deleteProduct(String id) {
        return productRepository.deleteProduct(id);
    }
}
