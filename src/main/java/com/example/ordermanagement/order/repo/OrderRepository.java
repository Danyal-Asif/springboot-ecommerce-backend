package com.example.ordermanagement.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermanagement.order.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    
}
