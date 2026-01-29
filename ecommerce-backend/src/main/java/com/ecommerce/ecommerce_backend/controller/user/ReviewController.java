package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.review.ReviewRequestDTO;
import com.ecommerce.ecommerce_backend.dto.review.ReviewResponseDTO;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/{productId}")
    public ReviewResponseDTO addReview(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long productId,
            @RequestBody ReviewRequestDTO dto) {

        User user = authService.getUserById(userId);

        Product product = new Product();
        product.setId(productId);

        return mapToDto(
                reviewService.addReview(user, product, dto.getRating(), dto.getComment())
        );
    }

    @GetMapping("/{productId}")
    public Map<String, Object> getReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Product product = new Product();
        product.setId(productId);

        List<ReviewResponseDTO> allReviews = reviewService.getProductReviews(product)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // Manual pagination
        int totalElements = allReviews.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<ReviewResponseDTO> paginatedReviews = startIndex < totalElements
                ? allReviews.subList(startIndex, endIndex)
                : List.of();

        Map<String, Object> response = new HashMap<>();
        response.put("content", paginatedReviews);
        response.put("totalElements", totalElements);
        response.put("totalPages", (int) Math.ceil((double) totalElements / size));
        response.put("size", size);
        response.put("number", page);
        response.put("first", page == 0);
        response.put("last", endIndex >= totalElements);

        return response;
    }

    @GetMapping("/{productId}/can-review")
    public Map<String, Boolean> canReview(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long productId) {

        User user = authService.getUserById(userId);
        Product product = new Product();
        product.setId(productId);

        boolean canReview = reviewService.canUserReview(user, product);
        return Map.of("canReview", canReview);
    }

    private ReviewResponseDTO mapToDto(com.ecommerce.ecommerce_backend.model.Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setUserName(review.getUser().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setVerifiedPurchase(review.isVerifiedPurchase());
        return dto;
    }
}
