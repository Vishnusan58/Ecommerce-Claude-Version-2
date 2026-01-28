package com.ecommerce.ecommerce_backend.service.product;

import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import com.ecommerce.ecommerce_backend.repository.PremiumSubscriptionRepository;
import com.ecommerce.ecommerce_backend.repository.ProductSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;

    public ProductService(ProductRepository productRepository,
                          PremiumSubscriptionRepository premiumSubscriptionRepository) {
        this.productRepository = productRepository;
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;
    }

    /**
     * Get all products with premium early-access logic
     */
    public Page<Product> getProducts(
            User user,
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Double minRating
    ) {

        // 🧭 Sorting
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // 🔍 Build dynamic filters
        Specification<Product> spec =
                ProductSpecification.withFilters(
                        keyword,
                        categoryId,
                        minPrice,
                        maxPrice,
                        minRating
                );

        Page<Product> pageResult =
                productRepository.findAll(spec, pageable);

        // 👤 GUEST USER → NOT PREMIUM
        if (user == null) {
            LocalDateTime cutoff = LocalDateTime.now().minusHours(24);

            return new PageImpl<>(
                    pageResult.getContent().stream()
                            .filter(p -> p.getCreatedAt().isBefore(cutoff))
                            .toList(),
                    pageable,
                    pageResult.getTotalElements()
            );
        }

        // ⭐ LOGGED-IN USER → CHECK PREMIUM
        boolean isPremium =
                premiumSubscriptionRepository.existsByUserAndActiveTrue(user);

        if (!isPremium) {
            LocalDateTime cutoff = LocalDateTime.now().minusHours(24);

            return new PageImpl<>(
                    pageResult.getContent().stream()
                            .filter(p -> p.getCreatedAt().isBefore(cutoff))
                            .toList(),
                    pageable,
                    pageResult.getTotalElements()
            );
        }

        // ⭐ PREMIUM USER → FULL ACCESS
        return pageResult;
    }


    /**
     * Get product by ID
     */
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Search products by name
     */
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * Compare products (used for compare feature)
     */
    public List<Product> compareProducts(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}
