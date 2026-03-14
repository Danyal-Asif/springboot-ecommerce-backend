package com.example.ordermanagement.order.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public String profile(Model model,Principal principal){
        User user=userService.findUserByEmail(principal.getName());
        model.addAttribute("firstName", user.getName().split(" ")[0]);
        model.addAttribute("lastName", user.getName().split(" ")[1]);
        model.addAttribute("email", principal.getName());
        model.addAttribute("phone", null);
        model.addAttribute("city", null);
        model.addAttribute("address", null);
        model.addAttribute("postalCode", null);
         model.addAttribute("showPasswordForm", false);
        // model.addAttribute("phone", user.getAddresses().get(1).getPhone());
        // model.addAttribute("city", user.getAddresses().get(1).getCity());
        // model.addAttribute("address", user.getAddresses().get(1).getAddress());
        return "profile";
    }

    @GetMapping("/password")
    public String password(Model model){

    model.addAttribute("showPasswordForm", true);

    return "profile";
}
}
