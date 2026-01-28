package com.ecommerce.ecommerce_backend.service.seller;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.Category;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.CategoryRepository;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SellerProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SellerProductService(ProductRepository productRepository,
                                CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product addProduct(User seller, Product product, Long categoryId) {
        validateSeller(seller);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setSeller(seller);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product updateProduct(User seller, Long productId, Product updated) {
        validateSeller(seller);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("Unauthorized product access");
        }

        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setStockQuantity(updated.getStockQuantity());

        return productRepository.save(product);
    }

    public void disableProduct(User seller, Long productId) {
        validateSeller(seller);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("Unauthorized product access");
        }

        product.setStockQuantity(0);
        productRepository.save(product);
    }

    public List<Product> getSellerProducts(User seller) {
        validateSeller(seller);
        return productRepository.findBySeller(seller);
    }

    private void validateSeller(User seller) {
        if (seller == null || seller.getRole() != UserRole.SELLER) {
            throw new RuntimeException("Seller access only");
        }

        if (!seller.isSellerVerified()) {
            throw new RuntimeException(
                    "Seller account is not verified by admin"
            );
        }
    }
}
