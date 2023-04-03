package com.getontop.challenge.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PaymentException400.class)
    public final ResponseEntity<Object> handleBadRequestException(PaymentException400 ex, WebRequest request) {
        log.error("Payment exception. status: {}. message: {}", 400, ex.getMessage());
        String localTransactionId = "";
        if (ex.getLocalTransactionId() != null) {
            localTransactionId = ex.getLocalTransactionId().toString();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), localTransactionId);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException500.class)
    public final ResponseEntity<Object> handleInternalServerErrorException(PaymentException500 ex, WebRequest request) {
        log.error("Payment exception. status: {}. message: {}", 500, ex.getMessage());
        String localTransactionId = "";
        if (ex.getLocalTransactionId() != null) {
            localTransactionId = ex.getLocalTransactionId().toString();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), localTransactionId);
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
