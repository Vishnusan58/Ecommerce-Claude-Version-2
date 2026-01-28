package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.Category;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategory(Category category);

    List<Product> findBySeller(User seller);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCreatedAtBefore(LocalDateTime cutoff);

    Page<Product> findAll(Pageable pageable);
}
