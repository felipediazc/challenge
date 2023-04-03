package com.getontop.challenge.adapter;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.mock.PaymentProviderMock;
import com.getontop.challenge.port.PaymentProvider;
import com.getontop.challenge.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {PaymentProviderMock.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class DefaultPaymentProviderImplTest {

    @Autowired
    private PaymentProvider paymentProvider;

    @Test
    void testSuccessPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentSuccessDto = gson.fromJson(Constants.PAYMENT_SUCCESS_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentSuccessResponseDto = gson.fromJson(Constants.PAYMENT_SUCCESS_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = paymentProvider.doPayment(createPaymentSuccessDto, null);
        assertEquals(createPaymentSuccessResponseDto, paymentResponse);
    }


    @Test
    void testPaymentInvalidExceptionBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentInvalidDto = gson.fromJson(Constants.PAYMENT_INVALID_BODY, CreatePaymentDto.class);
        assertThrows(PaymentException400.class, () -> {
            paymentProvider.doPayment(createPaymentInvalidDto, null);
        });
    }

    @Test
    void testRejectedBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentRejectedDto = gson.fromJson(Constants.PAYMENT_REJECTED_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentRejectedResponseDto = gson.fromJson(Constants.PAYMENT_REJECTED_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = paymentProvider.doPayment(createPaymentRejectedDto, null);
        assertEquals(createPaymentRejectedResponseDto, paymentResponse);
    }

    @Test
    void testTimeoutBodyPayment() {
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentTimeoutDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentTimeoutResponseDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentResponseDto paymentResponse = paymentProvider.doPayment(createPaymentTimeoutDto, null);
        assertEquals(createPaymentTimeoutResponseDto, paymentResponse);
    }
}