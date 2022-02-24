package com.proiect.SCD.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class AppException extends HttpClientErrorException {
    public AppException(HttpStatus status, String message) {
        super(status, message);
    }
}
