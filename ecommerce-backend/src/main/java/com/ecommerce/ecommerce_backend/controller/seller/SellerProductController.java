package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.product.ProductRequestDTO;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/products")
public class SellerProductController {

    private final SellerProductService productService;
    private final AuthService authService;

    public SellerProductController(SellerProductService productService,
                                   AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @RequestBody ProductRequestDTO dto) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setBrand(dto.getBrand());
        product.setPremiumEarlyAccess(dto.isPremiumEarlyAccess());

        Product saved = productService.addProduct(seller, product, dto.getCategoryId());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @PathVariable Long productId,
            @RequestBody ProductRequestDTO dto) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        Product updated = new Product();
        updated.setName(dto.getName());
        updated.setDescription(dto.getDescription());
        updated.setPrice(dto.getPrice());
        updated.setOriginalPrice(dto.getOriginalPrice());
        updated.setStockQuantity(dto.getStockQuantity());
        updated.setImageUrl(dto.getImageUrl());
        updated.setBrand(dto.getBrand());
        updated.setPremiumEarlyAccess(dto.isPremiumEarlyAccess());

        try {
            Product result = productService.updateProduct(seller, productId, updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getSellerProducts(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        List<Product> allProducts = productService.getSellerProducts(seller);

        // Manual pagination
        int totalElements = allProducts.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<Product> paginatedProducts = startIndex < totalElements
                ? allProducts.subList(startIndex, endIndex)
                : List.of();

        Map<String, Object> response = new HashMap<>();
        response.put("content", paginatedProducts);
        response.put("totalElements", totalElements);
        response.put("totalPages", (int) Math.ceil((double) totalElements / size));
        response.put("size", size);
        response.put("number", page);
        response.put("first", page == 0);
        response.put("last", endIndex >= totalElements);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> disableProduct(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @PathVariable Long productId) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        try {
            productService.disableProduct(seller, productId);
            return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
