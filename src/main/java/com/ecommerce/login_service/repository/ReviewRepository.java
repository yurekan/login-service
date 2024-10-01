package com.ecommerce.login_service.repository;


import com.ecommerce.login_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByVehicleId(int vehicleid);
}
