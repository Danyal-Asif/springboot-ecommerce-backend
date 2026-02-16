package com.example.ordermanagement.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ordermanagement.order.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c.id,c.user_id,c.product_id,c.quantity,c.price,c.totalPrice FROM Cart c where user_id=:user_id and product_id =:product_id")
    Cart getCartItemsOnUserId(@Param("user_id") Long user_id,@Param("product_id") Long product_id);
}
