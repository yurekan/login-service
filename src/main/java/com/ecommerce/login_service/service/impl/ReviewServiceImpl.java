package com.ecommerce.login_service.service.impl;

import com.ecommerce.login_service.dto.ReviewDto;
import com.ecommerce.login_service.exceptions.ReviewNotFoundException;
import com.ecommerce.login_service.exceptions.VehicleNotFoundException;
import com.ecommerce.login_service.model.Review;
import com.ecommerce.login_service.model.Vehicle;
import com.ecommerce.login_service.repository.ReviewRepository;
import com.ecommerce.login_service.repository.VehicleRepository;
import com.ecommerce.login_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public ReviewDto createReview(int vehicleid, ReviewDto reviewDto){
        Review review = mapToEntity(reviewDto);
        Vehicle vehicle = vehicleRepository.findById(vehicleid).orElseThrow(() ->
                new VehicleNotFoundException("Vehicle associated with review not found"));

        review.setVehicle(vehicle);

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByVehicleId(int vehicleid){
        List<Review> reviews = reviewRepository.findByVehicleId(vehicleid);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Vehicle vehicle = vehicleRepository.findById(pokemonId).orElseThrow(() -> new VehicleNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getVehicle().getId() != vehicle.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a vehicle");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int vehicleid, int reviewId, ReviewDto reviewDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleid).orElseThrow(() -> new VehicleNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getVehicle().getId() != vehicle.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a vehicle");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setCreatedOn(LocalDate.now());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int vehicleId, int reviewId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getVehicle().getId() != vehicle.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a vehicle");
        }

        reviewRepository.delete(review);
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }
}
