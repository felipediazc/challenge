package com.getontop.challenge.adapter;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.exception.PaymentException;
import com.getontop.challenge.mock.PaymentMock;
import com.getontop.challenge.port.Payment;
import com.getontop.challenge.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@ContextConfiguration(classes = {PaymentMock.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class DefaultPaymentImplTest {

    @Autowired
    private Payment payment;

    @Test
    void testSuccessPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentSuccessDto = gson.fromJson(Constants.PAYMENT_SUCCESS_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentSuccessResponseDto = gson.fromJson(Constants.PAYMENT_SUCCESS_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = payment.doPayment(createPaymentSuccessDto);
        assertEquals(createPaymentSuccessResponseDto, paymentResponse);
    }


    @Test
    void testPaymentInvalidExceptionBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentInvalidDto = gson.fromJson(Constants.PAYMENT_INVALID_BODY, CreatePaymentDto.class);
        assertThrows(PaymentException.class, () -> {
            payment.doPayment(createPaymentInvalidDto);
        });
    }

    @Test
    void testRejectedBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentRejectedDto = gson.fromJson(Constants.PAYMENT_REJECTED_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentRejectedResponseDto = gson.fromJson(Constants.PAYMENT_REJECTED_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = payment.doPayment(createPaymentRejectedDto);
        assertEquals(createPaymentRejectedResponseDto, paymentResponse);
    }

    @Test
    void testTimeoutBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentTimeoutDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentTimeoutResponseDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = payment.doPayment(createPaymentTimeoutDto);
        assertEquals(createPaymentTimeoutResponseDto, paymentResponse);
    }
}