package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ordermanagement.dto.AddressDTO;
import com.example.ordermanagement.dto.UserDto;
import com.example.ordermanagement.entity.Address;
import com.example.ordermanagement.entity.AddressType;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        String[] names = user.getName().split(" ",2);
        List<Address> addresses=user.getAddresses();
        if(addresses.isEmpty()){
            model.addAttribute("phone", null);
        model.addAttribute("city", null);
        model.addAttribute("address", null);
        model.addAttribute("postalCode", null);
        }
        else{
            model.addAttribute("phone", addresses.get(0).getPhone());
        model.addAttribute("city", addresses.get(0).getCity());
        model.addAttribute("address", addresses.get(0).getAddress());
        model.addAttribute("postalCode", addresses.get(0).getPostalCode());
        }

        model.addAttribute("firstName", names[0]);
        model.addAttribute("lastName", names.length > 1 ? names[1] : "");
        model.addAttribute("email", principal.getName());
        model.addAttribute("showPasswordForm", false);

        // model.addAttribute("phone", user.getAddresses().get(1).getPhone());
        // model.addAttribute("city", user.getAddresses().get(1).getCity());
        // model.addAttribute("address", user.getAddresses().get(1).getAddress());
        return "profile";
    }

    @GetMapping("/password")
    public String password(Model model) {
        model.addAttribute("showPasswordForm", true);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(Principal principal,
            @ModelAttribute("user") UserDto userDto,@ModelAttribute("address") AddressDTO addressDto) {

        try {

            if (principal.getName().equals(userDto.getEmail())) {
                User user=userService.findUserByEmail(principal.getName());
                Address userAddress=new Address(user, addressDto.phone(), 
                addressDto.address(), addressDto.city(), addressDto.postalCode(),AddressType.valueOf(addressDto.addressType()));
                List<Address> addressList=new ArrayList<>(List.of(userAddress));
                userService.updateUser(
                    userDto.getFirstName(),
                    userDto.getLastName(),
                        userDto.getEmail(),
                        addressList);   
            }

        } catch (Exception e) {

            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }

        return "redirect:/welcome";
    }
}
