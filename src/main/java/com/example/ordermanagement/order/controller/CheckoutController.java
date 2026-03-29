package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ordermanagement.entity.Address;
import com.example.ordermanagement.entity.AddressType;
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
    public String checkout(HttpSession session, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());

        List<String> home = new ArrayList<>();
        List<String> business = new ArrayList<>();
        List<String> other = new ArrayList<>();
        
        List<Address> list = user.getAddresses();
        String phoneNumber=list.get(0).getPhone();
        for (Address address : list) {
            System.out.println();
            if (address.getAddressType().equals(AddressType.valueOf("HOME"))) {
                home.add(address.getAddress());
                home.add(address.getCity()+", "+address.getPostalCode());
            } else if (address.getAddressType().equals(AddressType.valueOf("BUSINESS"))) {
                business.add(address.getAddress());
                business.add(address.getCity()+", "+address.getPostalCode());
            } else {
                other.add(address.getAddress());
                other.add(address.getCity()+", "+address.getPostalCode());
            }
        }
        model.addAttribute("email", user.getEmail());
        List<CartDTO> cartItems = cartService.getAllFromCart(user.getId());
        double checkoutPrice = 0;
        if (cartItems != null) {
            for (CartDTO cart : cartItems) {
                checkoutPrice += cart.totalPrice();
            }
        }
        
        double shipping = 0;
        CheckoutDTO checkoutDetail = new CheckoutDTO(checkoutPrice, shipping, checkoutPrice + shipping);
        Map<String, List<String>> address = new HashMap<>();

        address.put("Home", home);
        address.put("Business", business);
        address.put("Other", other);

        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("address", address);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("checkoutDetail", checkoutDetail);
        return "checkout";
    }
}
