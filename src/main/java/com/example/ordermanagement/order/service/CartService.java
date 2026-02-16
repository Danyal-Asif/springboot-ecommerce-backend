package com.example.ordermanagement.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.order.model.Cart;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.repo.CartRepository;
import com.example.ordermanagement.service.UserService;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public boolean add(Long product_id,Long user_id){
        Optional<Cart> cart=checkProductExistInCart(product_id,user_id);
        if(cart.isEmpty()){
            Product product=productService.getProduct(product_id);
            if(product==null){ return false;}
            Cart newCart=new Cart(userService.findUserById(user_id), product, product.getPrice(), 1, product.getPrice()); // this product does not exist in the db for the user_id so initally setting prices
            cartRepository.save(newCart);
            return true;
        }
        else{
            // product exists in the cart already
            Cart updatedCart=cart.get();
            updatedCart.setQuantity(updatedCart.getQuantity()+1);
            updatedCart.setTotalPrice(updatedCart.getPrice()*updatedCart.getQuantity());
            cartRepository.save(updatedCart);
            return true;
        }
    }

    private Optional<Cart> checkProductExistInCart(Long product_id,Long user_id){
        Cart cart=null;
        cart=cartRepository.findByUserIdAndProductId(user_id, product_id);
        if(cart==null){
            return Optional.empty();
        }
        return Optional.of(cart);
    }

    public List<Cart> getAllFromCart(Iterable<Long> user_id){
        List<Cart> cartItems=cartRepository.findAllById(user_id);
        if(cartItems.isEmpty()){
            return null;
        }
        return cartItems;
    }
}
