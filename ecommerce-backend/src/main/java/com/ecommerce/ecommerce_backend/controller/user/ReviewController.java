package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.review.ReviewRequestDTO;
import com.ecommerce.ecommerce_backend.dto.review.ReviewResponseDTO;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    public ReviewController(ReviewService reviewService,
                            AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

    @PostMapping
    public ReviewResponseDTO addReview(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ReviewRequestDTO dto) {

        User user = authService.getUserById(userId);

        Product product = new Product();
        product.setId(dto.getProductId());

        return mapToDto(
                reviewService.addReview(user, product, dto.getRating(), dto.getComment())
        );
    }

    @GetMapping("/{productId}")
    public List<ReviewResponseDTO> getReviews(@PathVariable Long productId) {

        Product product = new Product();
        product.setId(productId);

        return reviewService.getProductReviews(product)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReviewResponseDTO mapToDto(com.ecommerce.ecommerce_backend.model.Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setUserName(review.getUser().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setVerifiedPurchase(review.isVerifiedPurchase());
        return dto;
    }
}
