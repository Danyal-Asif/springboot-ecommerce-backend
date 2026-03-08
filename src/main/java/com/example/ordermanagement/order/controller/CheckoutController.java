package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.order.DTO.CartDTO;
import com.example.ordermanagement.order.DTO.CheckoutDTO;
import com.example.ordermanagement.order.service.CartService;
import com.example.ordermanagement.service.UserService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private UserService userService;
     @Autowired
    private CartService cartService;

    @GetMapping
    public String checkout(HttpSession session, Model model, Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("email",user.getEmail());
         List<CartDTO> cartItems = cartService.getAllFromCart(user.getId());
        double checkoutPrice = 0;
        if (cartItems != null) {
            for (CartDTO cart : cartItems) {
                checkoutPrice += cart.totalPrice();
            }
        }
        double shipping=0;
        CheckoutDTO checkoutDetail=new CheckoutDTO(checkoutPrice,shipping,checkoutPrice+shipping);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("checkoutDetail", checkoutDetail);
        return "checkout";
    }
}
