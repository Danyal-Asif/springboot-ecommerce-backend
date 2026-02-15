package com.example.ordermanagement.order.DTO;

import com.example.ordermanagement.order.model.ProductCategory;

public class ProductDTO {
	private Long id;
	private String name;
	private double price;
	private int inStock;
	private ProductCategory category;

	public ProductDTO(Long id,String name, double price, int inStock,ProductCategory category) {
		this.name = name;
		this.price = price;
		this.inStock = inStock;
		this.category=category;
		this.id=id;
	}
    
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}
}
