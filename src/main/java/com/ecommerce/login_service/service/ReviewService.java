package com.ecommerce.login_service.service;

import com.ecommerce.login_service.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int vehicleId, ReviewDto reviewDto);
    // List<ReviewDto> getAllReviews(int vehicleid);
    ReviewDto getReviewById(int reviewId, int vehicleId);
    List<ReviewDto> getReviewByVehicleId(int id);
    ReviewDto updateReview(int vehicleId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int vehicleId, int reviewId);
}
