package com.getontop.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentException400 extends RuntimeException {
    public PaymentException400(String message) {
        super(message);
    }
}
