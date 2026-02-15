package com.example.ordermanagement.order.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.order.DTO.OrderDTO;
import com.example.ordermanagement.order.model.Order;
import com.example.ordermanagement.order.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping
	public List<Order> listAllOrders(){
	return  orderService.getAllOrders();
	}
	
	@PostMapping
	public String saveOrder(@RequestBody OrderDTO orderDTO)
	{
		orderService.saveOrder(orderDTO);
		return "Order added Successfully";
	}
	
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderService.getOrder(id);
	}
	
	@PutMapping("/{id}/status")
	public Order updateOrderStatus(@PathVariable Long id,@RequestParam String status) {
		Order order=orderService.getOrder(id);
		return orderService.updateOrder(order, status);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
	}
}

