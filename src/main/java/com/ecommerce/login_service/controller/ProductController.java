package com.ecommerce.login_service.controller;

import com.ecommerce.login_service.dto.ProductDto;
import com.ecommerce.login_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/vehicles")
    public List<ProductDto> getAllVehicles(){
        return productService.getAllProduct();
    }

    @GetMapping("vehicle/{id}")
    public ResponseEntity<ProductDto> getVehicleById(@PathVariable(value = "id") int vehicleId){
        ProductDto productDto = productService.getProductById(vehicleId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("vehicle/add")
    public ResponseEntity<ProductDto> createVehicle(@RequestBody ProductDto productDto){
        ProductDto _productDto = productService.createProduct(productDto);
        return new ResponseEntity<>(_productDto, HttpStatus.CREATED);
    }

    @PutMapping("vehicle/{id}/update")
    public ResponseEntity<ProductDto> updateVehicle(@RequestBody ProductDto productDto, @PathVariable(value = "id") int vehicleId){
        ProductDto _productDto = productService.updateProduct(productDto, vehicleId);
        return new ResponseEntity<>(_productDto, HttpStatus.OK);
    }

    @DeleteMapping("vehicle/{id}/delete")
    public void deleteVehicle(@PathVariable(value = "id") int vehicleId){
        productService.deleteProduct(vehicleId);
    }
}
