package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/find-all-products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{productNumber}")
    public Product getProductUnit(@PathVariable int productNumber) {
        return productService.getProductUnit(productNumber);
    }

    // 1. 제품테이블에 새로운 제품 1개를 추가하는 API
    @PostMapping("/post/product")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // 2. 제품테이블에 여러 제품을 추가하는 API
    @PostMapping("/post/productlist")
    public List<Product> saveProductList(@RequestBody List<Product> productList) {
        return productService.saveProductList(productList);
    }

    // 4. 제품테이블의 정보를 수정하는 API
    @PutMapping("/put/product")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    // 5. 제품테이블의 정보를 삭제하는 API
    @DeleteMapping("/delete/product")
    public String deleteProduct(@RequestParam String id) {
        return "제품번호 : " + productService.deleteProduct(id) + " 가 삭제되었습니다.";
    }
}
