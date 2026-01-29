package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.model.*;
import com.ecommerce.ecommerce_backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(User user, Product product, int rating, String comment) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setVerifiedPurchase(true);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public List<Review> getProductReviews(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public boolean canUserReview(User user, Product product) {
        // User can review if they haven't already reviewed this product
        return !reviewRepository.existsByUserAndProduct(user, product);
    }
}
