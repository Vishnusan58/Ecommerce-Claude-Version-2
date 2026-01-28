package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.product.ProductRequestDTO;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Product addProduct(
            @RequestHeader("X-SELLER-ID") Long sellerId,
            @RequestBody ProductRequestDTO dto) {

        User seller = authService.getUserById(sellerId);

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        return productService.addProduct(seller, product, dto.getCategoryId());
    }

    @PutMapping("/{productId}")
    public Product updateProduct(
            @RequestHeader("X-SELLER-ID") Long sellerId,
            @PathVariable Long productId,
            @RequestBody ProductRequestDTO dto) {

        User seller = authService.getUserById(sellerId);

        Product updated = new Product();
        updated.setName(dto.getName());
        updated.setDescription(dto.getDescription());
        updated.setPrice(dto.getPrice());
        updated.setStockQuantity(dto.getStockQuantity());

        return productService.updateProduct(seller, productId, updated);
    }

    @GetMapping
    public List<Product> getSellerProducts(
            @RequestHeader("X-SELLER-ID") Long sellerId) {

        User seller = authService.getUserById(sellerId);
        return productService.getSellerProducts(seller);
    }

    @DeleteMapping("/{productId}")
    public void disableProduct(
            @RequestHeader("X-SELLER-ID") Long sellerId,
            @PathVariable Long productId) {

        User seller = authService.getUserById(sellerId);
        productService.disableProduct(seller, productId);
    }
}
