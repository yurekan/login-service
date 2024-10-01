package com.ecommerce.login_service.dto;

import com.ecommerce.login_service.model.Review;
import com.ecommerce.login_service.model.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    private int id;
    private String brand;
    private String variant;
    private Date year;
    private Type type;
    private double price;
    private boolean availability;
    private int rating;
}
