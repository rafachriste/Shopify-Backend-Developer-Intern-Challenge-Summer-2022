package com.shopify.inventorytracking.controller;

import com.shopify.inventorytracking.model.Product;
import com.shopify.inventorytracking.repository.ProductRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CommonsLog
public class MainController {

    private final ProductRepository productRepository;

    public MainController(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public ResponseEntity<?> get(@RequestParam Long productId) {
        if(productRepository.findById(productId).isPresent()) {
            return ResponseEntity.ok(productRepository.findById(productId));
        }
        return ResponseEntity.badRequest().body("Product not found");
    }

    @PutMapping("update")
    public ResponseEntity<?> put(@RequestBody Product product) {
        if(productRepository.findById(product.getProductId()).isPresent()) {
            productRepository.save(product);
            return ResponseEntity.ok("Successfully updated data");
        }

        return ResponseEntity.badRequest().body("Product not found");
    }

    @DeleteMapping(value = "delete", params = {"productId"})
    public ResponseEntity<?> delete(@RequestParam Long productId) {
        if(productRepository.findById(productId).isPresent()){
            productRepository.deleteById(productId);
            return ResponseEntity.ok("Deleted Successfully");
        }

        return ResponseEntity.badRequest().body("Product not found");
    }

    @GetMapping("inventory")
    public ResponseEntity<?> inventory() {
        return ResponseEntity.ok(productRepository.findAll());

    }
}
