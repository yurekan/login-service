package com.ecommerce.login_service.repository;

import com.ecommerce.login_service.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Optional<Vehicle> findByBrand(String brand);
    Optional<Vehicle> findByType(String type);
}
