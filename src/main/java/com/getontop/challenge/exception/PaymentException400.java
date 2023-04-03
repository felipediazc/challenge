package com.getontop.challenge.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentException400 extends RuntimeException {

    private UUID localTransactionId;

    public PaymentException400(String message, UUID localTransactionId) {
        super(message);
        this.localTransactionId = localTransactionId;
    }

    public PaymentException400(String message) {
        super(message);
    }
}
