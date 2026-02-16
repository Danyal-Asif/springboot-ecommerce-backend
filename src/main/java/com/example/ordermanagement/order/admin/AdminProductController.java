package com.example.ordermanagement.order.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ordermanagement.order.DTO.ProductDTO;
import com.example.ordermanagement.order.mapper.ProductMapper;
import com.example.ordermanagement.order.model.Product;
import com.example.ordermanagement.order.model.ProductCategory;
import com.example.ordermanagement.order.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adminUI")
public class AdminProductController {

    @Autowired
	ProductService productService; 

	@Autowired
	ProductMapper productMapper;
	
    @PostMapping("/initEdit")
	public String initiateEditProduct(@RequestParam Long productId, Model model){
		Product product = productService.getProduct(productId);
		model.addAttribute("categories", ProductCategory.values());
		model.addAttribute("category", product.getCategory());
		System.out.println(product.getCategory());
		model.addAttribute("product", product);
        return "editProduct";
	}

    @PostMapping("/deleteProduct")
	public String deleteProduct(@RequestParam Long productId, Model model){
		productService.deleteProduct(productId);
		List<Product> products=productService.getAllProducts();
		List<ProductDTO> productDTOs = products.stream().map(productMapper::toDto).toList();
		model.addAttribute("products", productDTOs);
		model.addAttribute("categories", ProductCategory.values());

		return "edit-product-List";
	}

    @GetMapping("/products/category")
	public String adminViewByCategory(@RequestParam(required = false) String pCategory, HttpSession session, Model model) {
		List<Product> products;
		if (pCategory == null || pCategory.isBlank()) {
			products = productService.getAllProducts();
		} else {
			products = productService.getProductByCategory(ProductCategory.valueOf(pCategory));
		}
		List<ProductDTO> productDTOs = products.stream().map(productMapper::toDto).toList();
		model.addAttribute("products", productDTOs);
		model.addAttribute("categories", ProductCategory.values());

		return "edit-product-List";
	}
    @PostMapping("/edit")
	public String editProduct(@ModelAttribute ProductDTO pd) {
		productService.editProduct(pd);
		System.out.println("*********** Product updated Successfully! *************");
		return "redirect:/admin";
	}
}

