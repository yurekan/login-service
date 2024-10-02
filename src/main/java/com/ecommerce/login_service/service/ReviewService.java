package com.ecommerce.login_service.service;

import com.ecommerce.login_service.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int productId, ReviewDto reviewDto);
    ReviewDto getReviewById(int reviewId, int productId);
    List<ReviewDto> getReviewByProductId(int id);
    ReviewDto updateReview(int productId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int productId, int reviewId);
}
