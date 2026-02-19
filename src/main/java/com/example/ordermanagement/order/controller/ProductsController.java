package com.example.ordermanagement.order.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.order.DTO.ProductDTO;
import com.example.ordermanagement.order.mapper.ProductMapper;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.model.ProductCategory;
import com.example.ordermanagement.order.service.ProductService;
import com.example.ordermanagement.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	ProductService productService;

    @Autowired
    private UserService userService; 

	@Autowired
	ProductMapper productMapper;

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}

	@PostMapping("/save")
	public String addProduct(@ModelAttribute ProductDTO pd) {
		productService.saveProduct(pd);
		System.out.println("*********** Product added Successfully! *************");
		return "redirect:/dashboard";
	}

	@GetMapping("/add")
	public String addProductPage(Model model) {
		model.addAttribute("categories", ProductCategory.values());
		return "add-product";
	}

	@GetMapping("/catPd")
	public String products(@RequestParam(required = false) String pCategory, HttpSession session, Model model,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("name", "Welcome, " + user.getName() + " 😎");
		List<Product> products;
		if (pCategory == null || pCategory.isBlank()) {
			products = productService.getAllProducts();
		} else {
			products = productService.getProductByCategory(ProductCategory.valueOf(pCategory));
		}
		List<ProductDTO> productDTOs = products.stream().map(productMapper::toDto).toList();
		model.addAttribute("products", productDTOs);
		model.addAttribute("categories", ProductCategory.values());

		return "product-page";
	}

}
