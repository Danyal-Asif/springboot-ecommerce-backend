package com.example.ordermanagement.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.exception.OutOfStockException;
import com.example.ordermanagement.order.DTO.OrderDTO;
import com.example.ordermanagement.order.model.Order;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.repo.OrderRepository;



@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ProductService productService;
	
	public List<Order> getAllOrders(){
		return orderRepo.findAll();
	}
	
	public Order saveOrder(OrderDTO orderDto) {
		Product pd=productService.getProduct(orderDto.getProductId());
		if(pd.instock<1) {
			throw new OutOfStockException("Product is out of stock.");
		}
		double price=pd.getPrice();
		double totalPrice=calculateTotalPrice(orderDto.getQuantity(), price);
		Order order =new Order(orderDto.getCustomerID(),orderDto.getProductId(),orderDto.getQuantity(),totalPrice,"");
		order.setStatus("Processing");
		orderRepo.save(order);
		System.out.println("Order saved Successfully");
		return order;
	}
	
	public Order getOrder(Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}
	
	public Order updateOrder(Order order,String status) {
		order.setStatus(status);
		return order;
	}
	
	public void deleteOrder(Long id) {
		orderRepo.deleteById(id);
		System.out.println("Order deleted Successfully");
	}
	
	public double calculateTotalPrice(int quantity,double price) {
		return quantity*price;
	}
	
	
	
}