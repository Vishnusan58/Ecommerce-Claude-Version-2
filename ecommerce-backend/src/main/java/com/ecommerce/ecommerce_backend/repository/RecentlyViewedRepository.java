package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.RecentlyViewed;
import com.ecommerce.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Long> {

    List<RecentlyViewed> findByUserOrderByViewedAtDesc(User user);
}