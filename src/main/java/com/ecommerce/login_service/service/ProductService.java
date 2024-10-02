package com.ecommerce.login_service.service;

import com.ecommerce.login_service.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProduct();
    ProductDto getProductById(int id);
    ProductDto updateProduct(ProductDto productDto, int id);
    void deleteProduct(int id);
}
