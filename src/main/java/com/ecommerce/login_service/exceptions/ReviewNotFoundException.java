package com.ecommerce.login_service.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2;
    public ReviewNotFoundException(String message) {
        super(message);
    }
}