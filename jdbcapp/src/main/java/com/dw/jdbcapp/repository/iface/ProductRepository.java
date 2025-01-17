package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.dto.ProductDTO;
import com.dw.jdbcapp.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(int productNumber);
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    int deleteProduct(int id);
    List<Product> getProductsBelowPrice(double price);
    String updateProductWithStock(int id, int stock);
    List<Product> getProductByProductName(String name);
    List<Product> getProductsByStockValue();
}
