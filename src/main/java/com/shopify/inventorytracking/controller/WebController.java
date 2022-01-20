package com.shopify.inventorytracking.controller;

import com.shopify.inventorytracking.model.Product;
import com.shopify.inventorytracking.repository.ProductRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@CommonsLog
public class WebController {

    private final ProductRepository productRepository;

    public WebController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("newProduct")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "newProduct";
    }

    @GetMapping("findProduct")
    public String findProduct(Model model){
        model.addAttribute("product", new Product());
        return "findProduct";
    }

    @PostMapping("save")
    public String save(@ModelAttribute Product product, Model model) {
        try {
            product = productRepository.save(product);
            model.addAttribute("product", product);

            return "productView";
        } catch (Exception e) {
            log.error(e);

            return "error";
        }
    }

    @GetMapping(value = "get", params = {"productId"})
    public String get(@ModelAttribute Product product, Model model) {
        Optional<Product> product1 = productRepository.findById(product.getProductId());
        if(product1.isPresent()) {
            model.addAttribute("product", product1.get());
            return "productView";
        }
        return "error";
    }
}
