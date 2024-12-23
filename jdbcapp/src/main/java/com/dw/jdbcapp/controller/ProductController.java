package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.dto.ProductDTO;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/find-all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK);
    }

    // 2024.12.13 - Q1. 제품번호를 기준으로 제품 정보를 조회하는 API
    @GetMapping("/products/{productNumber}")
    public ResponseEntity<Product> getProductById(@PathVariable int productNumber) {
        return new ResponseEntity<>(
                productService.getProductById(productNumber), HttpStatus.ACCEPTED);
    }

    // 2024.12.16 - Q1. 제품테이블에 새로운 제품 1개를 추가하는 API
    @PostMapping("/post/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.saveProduct(product), HttpStatus.CREATED);
    }

    // 2024.12.16 - Q2. 제품테이블에 여러 제품을 추가하는 API
    @PostMapping("/post/productList")
    public ResponseEntity<List<Product>> saveProductList(@RequestBody List<Product> productList) {
        return new ResponseEntity<>(
                productService.saveProductList(productList), HttpStatus.CREATED);
    }

    // 2024.12.16 - Q4. 제품테이블의 정보를 수정하는 API
    @PutMapping("/put/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.updateProduct(product), HttpStatus.OK);
    }

    // 2024.12.16 - Q5. 제품테이블의 정보를 삭제하는 API
    @DeleteMapping("/delete/product")
    public ResponseEntity<String> deleteProduct(@RequestParam int id) {
        return new ResponseEntity<>(
                "제품번호 : " + productService.deleteProduct(id) + " 가 삭제되었습니다.",
                HttpStatus.ACCEPTED);
    }

    // 2024.12.19 - Q5. 제품을 조회할 때 단가를 매개변수로 전달하고 해당 단가보다 싼 제품을 조회하는 API
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProductsBelowPrice(@RequestParam double price_below) {
        return new ResponseEntity<>(
                productService.getProductsBelowPrice(price_below), HttpStatus.ACCEPTED);
    }

    @PutMapping("/products/update")
    public ResponseEntity<String> updateProductWithStock(@RequestParam int id,@RequestParam int stock) {
        return new ResponseEntity<>(
                productService.updateProductWithStock(id, stock), HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<Product>> getProductByProductName(@PathVariable String name) {
        return new ResponseEntity<>(
                productService.getProductByProductName(name), HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/stockvalue")
    public ResponseEntity<List<ProductDTO>> getProductsByStockValue() {
        return new ResponseEntity<>(
                productService.getProductsByStockValue(), HttpStatus.ACCEPTED);
    }
}
