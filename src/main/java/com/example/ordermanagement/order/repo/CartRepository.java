package com.example.ordermanagement.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ordermanagement.order.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

      Cart findByUserIdAndProductId(Long user_id, Long product_id);

}
