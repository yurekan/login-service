package com.ecommerce.login_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "variant")
    private String variant;
    @Column(name = "year")
    private Date year;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Column(name = "price")
    private double price;
    @Column(name = "availability")
    private boolean availability;
    @Column(name = "rating")
    private int rating;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<Review>();
}
