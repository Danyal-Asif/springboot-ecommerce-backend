package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.order.DTO.CartDTO;
import com.example.ordermanagement.order.model.Cart;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.service.CartService;
import com.example.ordermanagement.order.service.ProductService;
import com.example.ordermanagement.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, Principal principal, HttpSession session) {

        User cust = userService.findUserByEmail(principal.getName());
        cartService.add(productId, cust.getId());
        return "redirect:/products/catPd";
    }

    @GetMapping("/mycart")
    public String showCart(HttpSession session, Model model, Principal principal) {

        // Customer user = (Customer) session.getAttribute("LOGGED_IN_USER");
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }

        List<CartDTO> cartItems = cartService.getAllFromCart(user.getId());
        double checkoutPrice = 0;
        if (cartItems != null) {
            for (CartDTO cart : cartItems) {
                checkoutPrice += cart.totalPrice();
            }
        }

        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("checkoutPrice", checkoutPrice);

        return "cart-page";
    }

    @PostMapping("/decrease")
    public String minusItem(@RequestParam Long productID, HttpSession session, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        Optional<Cart> cartItem=cartService.getByUserIdAndProductId(productID,user.getId());
        int productQuantity=cartItem.get().getQuantity();
        if(productQuantity<=1){
            cartService.deleteItemFromCart(cartItem.get());
        }
        else{
            Cart cart=cartItem.get();
            cart.setQuantity(productQuantity-1); 
            double totalPrice=cart.getTotalPrice()-cart.getPrice();
            cart.setTotalPrice(totalPrice);
            cartService.UpdateCartItem(cart);
        }
        return "redirect:/cart/mycart";
    }

    @PostMapping("/increase")
    public String plusItem(@RequestParam Long productID, HttpSession session, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        Optional<Cart> cartItem=cartService.getByUserIdAndProductId(productID,user.getId());
         int productQuantity=cartItem.get().getQuantity();
         if(cartItem.isPresent()){
            Cart cart=cartItem.get();
            cart.setQuantity(productQuantity+1); 
            double totalPrice=cart.getQuantity()*cart.getPrice();
            cart.setTotalPrice(totalPrice);
            cartService.UpdateCartItem(cart);
        }
        return "redirect:/cart/mycart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productID, HttpSession session, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        Optional<Cart> cartItem=cartService.getByUserIdAndProductId(productID,user.getId());
        if(cartItem.isPresent()){
            cartService.deleteItemFromCart(cartItem.get());
        }
        return "redirect:/cart/mycart";
    }

    @PostMapping("/empty")
    public String emptyCart(HttpSession session, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        cartService.emptyCart(user.getId());
         return "redirect:/cart/mycart";
    }
}