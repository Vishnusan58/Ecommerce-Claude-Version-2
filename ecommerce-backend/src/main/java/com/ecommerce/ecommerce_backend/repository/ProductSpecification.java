package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilters(
            String keyword,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Double minRating
    ) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 Keyword search (name OR description)
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("name")),
                                        pattern
                                ),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("description")),
                                        pattern
                                )
                        )
                );
            }

            // 🗂 Category filter
            if (categoryId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("category").get("id"),
                                categoryId
                        )
                );
            }

            // 💰 Minimum price
            if (minPrice != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice
                        )
                );
            }

            // 💰 Maximum price
            if (maxPrice != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice
                        )
                );
            }

            // ⭐ Minimum rating
            if (minRating != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("averageRating"),
                                minRating
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
