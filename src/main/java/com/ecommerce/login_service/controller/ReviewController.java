package com.ecommerce.login_service.controller;

import com.ecommerce.login_service.dto.ReviewDto;
import com.ecommerce.login_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/vehicle/{vehicleId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "vehicleId") int vehicleId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(vehicleId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/vehicle/{vehicleId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "vehicleId") int vehicleId) {
        return reviewService.getReviewByVehicleId(vehicleId);
    }

    @GetMapping("/vehicle/{vehicleId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/vehicle/{vehicleId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "vehicleId") int vehicleId, @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(vehicleId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/vehicle/{vehicleId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "vehicleId") int vehicleId, @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(vehicleId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}