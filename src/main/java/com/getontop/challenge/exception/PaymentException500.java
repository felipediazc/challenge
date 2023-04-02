package com.getontop.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentException500 extends RuntimeException {
    public PaymentException500(String message) {
        super(message);
    }
}
