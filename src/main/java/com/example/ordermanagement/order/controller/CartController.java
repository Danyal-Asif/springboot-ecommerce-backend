package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ordermanagement.entity.User;
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
    public String addToCart(@RequestParam Long productId,Principal principal, HttpSession session) {

        User cust = userService.findUserByEmail(principal.getName());
        cartService.add(productId, cust.getId());
        return "redirect:/products/catPd";
    }

    @GetMapping("/mycart")
    public String showCart(HttpSession session, Model model,Principal principal) {

        // Customer user = (Customer) session.getAttribute("LOGGED_IN_USER");
         User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }

        List<Cart> cartItems = user.getCart();
        double checkoutPrice = 0;
        if(cartItems!=null){
        for (Cart cart : cartItems) {
            System.out.println(cart.getpName());
            checkoutPrice += cart.getTotalPrice();
        }}

        model.addAttribute("name", user.getName());
        model.addAttribute("cartItems", user.getCart());
        model.addAttribute("checkoutPrice", checkoutPrice);
        if (user.getCart() == null) {
            model.addAttribute("newProduct", null);
        } else {

            model.addAttribute("newProduct",
                    user.getCart().size() == 0 ? null : user.getCart().get(user.getCart().size() - 1).getpName());
        }
        return "cart-page";
    }

    @PostMapping("/decrease")
    public String minusItem(@RequestParam Long productID, HttpSession session, Model model,Principal principal) {
         User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        List<Cart> custCart = user.getCart();

        Iterator<Cart> iterator = custCart.iterator();

        while (iterator.hasNext()) {
            Cart c = iterator.next();
            if (c.getId().equals(productID)) {
                if (c.getQuantity() <= 1) {
                    custCart.remove(c);
                } else {
                    c.setQuantity(c.getQuantity() - 1);
                    c.setTotalPrice(c.getPrice() * c.getQuantity());
                }
                break;
            }

        }
        List<Cart> cartItems = user.getCart();
        double checkoutPrice = 0;
        for (Cart cart : cartItems) {
            checkoutPrice += cart.getTotalPrice();
        }
        model.addAttribute("checkoutPrice", checkoutPrice);

        model.addAttribute("name", user.getName());
        model.addAttribute("cartItems", user.getCart());

        return "cart-page";
    }

    @PostMapping("/increase")
    public String plusItem(@RequestParam Long productID, HttpSession session, Model model,Principal principal) {
       User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        List<Cart> custCart = user.getCart();

        Iterator<Cart> iterator = custCart.iterator();

        while (iterator.hasNext()) {
            Cart c = iterator.next();
            if (c.getId().equals(productID)) {
                c.setQuantity(c.getQuantity() + 1);
                c.setTotalPrice(c.getPrice() * c.getQuantity());
                break;
            }

        }
        List<Cart> cartItems = user.getCart();
        double checkoutPrice = 0;
        for (Cart cart : cartItems) {
            checkoutPrice += cart.getTotalPrice();
        }
        model.addAttribute("checkoutPrice", checkoutPrice);

        model.addAttribute("name", user.getName());
        model.addAttribute("cartItems", user.getCart());

        return "cart-page";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productID, HttpSession session, Model model,Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/order/login";
        }
        List<Cart> custCart = user.getCart();

        Iterator<Cart> iterator = custCart.iterator();

        while (iterator.hasNext()) {
            Cart c = iterator.next();
            if (c.getId().equals(productID)) {
                custCart.remove(c);
                break;
            }

       }
       List<Cart> cartItems = user.getCart();
        double checkoutPrice = 0;
        for (Cart cart : cartItems) {
            checkoutPrice += cart.getTotalPrice();
        }
        model.addAttribute("checkoutPrice", checkoutPrice);

        model.addAttribute("name", user.getName());
        model.addAttribute("cartItems", user.getCart());

        return "cart-page";
    }
}
