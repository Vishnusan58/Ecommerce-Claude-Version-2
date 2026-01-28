package com.ecommerce.ecommerce_backend.dto.review;

public class ReviewSummaryDTO {

    private double averageRating;
    private int totalReviews;

    public ReviewSummaryDTO() {}

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public int getTotalReviews() { return totalReviews; }
    public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }
}
