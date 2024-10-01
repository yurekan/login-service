package com.ecommerce.login_service.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}