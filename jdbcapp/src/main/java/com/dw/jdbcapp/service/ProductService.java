package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.ProductDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.exception.ResourceNotFoundException;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dw.jdbcapp.dto.ProductDTO.fromProduct;

@Service
public class ProductService {
    @Autowired
    @Qualifier("productTemplateRepository")
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(int productNumber) {
        if (productNumber < 0) {
            throw new InvalidRequestException("존재하지 않는 제품번호: "
                    + productNumber);
        }
        return productRepository.getProductById(productNumber);
    }

    public Product saveProduct(Product product) {
        return productRepository.saveProduct(product);
    }

    public List<Product> saveProductList(List<Product> productList) {
        for (Product data : productList) {
            productRepository.saveProduct(data);
        }
        return productList;
    }

    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public int deleteProduct(int id) {
        return productRepository.deleteProduct(id);
    }

    public List<Product> getProductsBelowPrice(double price) {
        List<Product> products = productRepository.getProductsBelowPrice(price);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("해당되는 제품이 없습니다 : " + price + "보다 싼 제품");
        } else {
            return products;
        }
    }

    public String updateProductWithStock(int id, int stock) {
        return productRepository.updateProductWithStock(id, stock);
    }

    public List<Product> getProductByProductName(String name) {
        return productRepository.getProductByProductName(name);
    }

    // 12. 20 - Q10 ProductDTO를 아래 형식으로 추가하고 조회하는 API
    public List<ProductDTO> getProductsByStockValue() {
        List<Product> products = productRepository.getProductsByStockValue();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
//            productDTOList.add(fromProduct(product));
            productDTOList.add(new ProductDTO(product));
        }
        return productDTOList;
    }
}