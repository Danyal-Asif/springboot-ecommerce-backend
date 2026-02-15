package com.example.ordermanagement.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.model.ProductCategory;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> getProductByCategory(ProductCategory productCategory);
}
 