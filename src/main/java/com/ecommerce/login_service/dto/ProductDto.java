package com.ecommerce.login_service.dto;

import com.ecommerce.login_service.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private String brand;
    private String variant;
    private Date year;
    private Type type;
    private double price;
    private boolean availability;
    private int rating;
}
