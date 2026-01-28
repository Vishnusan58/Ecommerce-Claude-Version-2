package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.model.*;
import com.ecommerce.ecommerce_backend.repository.RecentlyViewedRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecentlyViewedService {

    private final RecentlyViewedRepository recentlyViewedRepository;

    public RecentlyViewedService(RecentlyViewedRepository recentlyViewedRepository) {
        this.recentlyViewedRepository = recentlyViewedRepository;
    }

    public void recordView(User user, Product product) {
        RecentlyViewed rv = new RecentlyViewed();
        rv.setUser(user);
        rv.setProduct(product);
        rv.setViewedAt(LocalDateTime.now());
        recentlyViewedRepository.save(rv);
    }

    public List<RecentlyViewed> getRecentlyViewed(User user) {
        return recentlyViewedRepository.findByUserOrderByViewedAtDesc(user);
    }
}
