package com.ecommerce.login_service.repository;

import com.ecommerce.login_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByBrand(String brand);
    Optional<Product> findByType(String type);
}
