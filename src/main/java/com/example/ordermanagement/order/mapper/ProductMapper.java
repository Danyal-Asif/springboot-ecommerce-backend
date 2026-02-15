package com.example.ordermanagement.order.mapper;
import org.springframework.stereotype.Component;

import com.example.ordermanagement.order.DTO.ProductDTO;
import com.example.ordermanagement.order.model.Product;


@Component
public class ProductMapper {
    public ProductDTO toDto(Product p){
        return new ProductDTO(p.getId(),p.getName(), p.getPrice(), p.getInStock(), p.getCategory());
    }
}
