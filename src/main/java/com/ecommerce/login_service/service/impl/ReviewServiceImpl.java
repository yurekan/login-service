package com.ecommerce.login_service.service.impl;

import com.ecommerce.login_service.dto.ReviewDto;
import com.ecommerce.login_service.exceptions.ProductNotFoundException;
import com.ecommerce.login_service.exceptions.ReviewNotFoundException;
import com.ecommerce.login_service.model.Product;
import com.ecommerce.login_service.model.Review;
import com.ecommerce.login_service.repository.ProductRepository;
import com.ecommerce.login_service.repository.ReviewRepository;
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
    private ProductRepository productRepository;

    @Override
    public ReviewDto createReview(int productId, ReviewDto reviewDto){
        Review review = mapToEntity(reviewDto);
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Vehicle associated with review not found"));

        review.setProduct(product);

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByProductId(int productId){
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getProduct().getId() != product.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a vehicle");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int productId, int reviewId, ReviewDto reviewDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getProduct().getId() != product.getId()) {
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
    public void deleteReview(int productId, int reviewId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Vehicle with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate vehicle not found"));

        if(review.getProduct().getId() != product.getId()) {
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
