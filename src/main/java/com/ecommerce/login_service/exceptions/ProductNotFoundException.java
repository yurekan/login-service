package com.ecommerce.login_service.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ProductNotFoundException(String message) {
        super(message);
    }
}