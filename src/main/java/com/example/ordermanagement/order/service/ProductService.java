package com.example.ordermanagement.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.order.DTO.ProductDTO;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.model.ProductCategory;
import com.example.ordermanagement.order.repo.ProductRepository;



@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public Product getProduct(Long id) {
		return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}
	
	public String saveProduct(ProductDTO productDto) {
		Product product=new Product(productDto.getName(),productDto.getPrice(),productDto.getInStock(),ProductCategory.valueOf(productDto.getCategory().name()));
		productRepo.save(product);
		return "Product Added Successfully";
	}

	public List<Product> getProductByCategory(ProductCategory category){
		return productRepo.getProductByCategory(category);
	}

	public String editProduct(ProductDTO productDTO){
		Product product=productRepo.findById(productDTO.getId()).orElse(null);
		product.setName(productDTO.getName());
		product.setCategory(productDTO.getCategory());
		product.setInstock(productDTO.getInStock());
		product.setPrice(productDTO.getPrice());
		productRepo.save(product);
		return "Product edited Successfully";
	}
	
	public void deleteProduct(Long id){
		productRepo.deleteById(id);
	}
}

