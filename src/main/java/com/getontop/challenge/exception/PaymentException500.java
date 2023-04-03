package com.getontop.challenge.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentException500 extends RuntimeException {

    private UUID localTransactionId;

    public PaymentException500(String message, UUID localTransactionId) {
        super(message);
        this.localTransactionId = localTransactionId;
    }

    public PaymentException500(String message) {
        super(message);
    }
}
