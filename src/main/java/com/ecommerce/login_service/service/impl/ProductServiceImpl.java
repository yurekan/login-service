package com.ecommerce.login_service.service.impl;

import com.ecommerce.login_service.dto.ProductDto;
import com.ecommerce.login_service.exceptions.ProductNotFoundException;
import com.ecommerce.login_service.model.Product;
import com.ecommerce.login_service.repository.ProductRepository;
import com.ecommerce.login_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto){
        Product product = productRepository.save(mapToEntity(productDto));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(int productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Vehicle with associated review not found"));
        return mapToDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, int productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Vehicle with associated review not found"));
        product.setId(productDto.getId());
        product.setBrand(productDto.getBrand());
        product.setVariant(productDto.getVariant());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setYear(productDto.getYear());
        product.setAvailability(productDto.isAvailability());
        return mapToDto(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setBrand(productDto.getBrand());
        product.setVariant(productDto.getVariant());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setYear(productDto.getYear());
        product.setAvailability(productDto.isAvailability());
        return product;
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setBrand(product.getBrand());
        productDto.setVariant(product.getVariant());
        productDto.setPrice(product.getPrice());
        productDto.setRating(product.getRating());
        productDto.setYear(product.getYear());
        productDto.setAvailability(product.isAvailability());
        return productDto;
    }
}
