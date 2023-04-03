package com.getontop.challenge.mock;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.port.PaymentProvider;
import com.getontop.challenge.util.Constants;
import com.getontop.challenge.util.PaymentConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;


import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PaymentProviderMock {

    @Bean
    public PaymentProvider paymentProviderComponent(){
        PaymentProvider paymentMock = mock(PaymentProvider.class);
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentSuccessDto = gson.fromJson(Constants.PAYMENT_SUCCESS_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentSuccessResponseDto = gson.fromJson(Constants.PAYMENT_SUCCESS_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentDto createPaymentRejectedDto = gson.fromJson(Constants.PAYMENT_REJECTED_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentRejectedResponseDto = gson.fromJson(Constants.PAYMENT_REJECTED_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentDto createPaymentTimeoutDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentTimeoutResponseDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_RESPONSE, CreatePaymentResponseDto.class);
        when(paymentMock.doPayment(createPaymentSuccessDto, null)).thenReturn(createPaymentSuccessResponseDto);
        when(paymentMock.doPayment(createPaymentRejectedDto, null)).thenReturn(createPaymentRejectedResponseDto);
        when(paymentMock.doPayment(createPaymentTimeoutDto, null)).thenReturn(createPaymentTimeoutResponseDto);

        /*Exception handling*/
        CreatePaymentDto createPaymentInvalidDto = gson.fromJson(Constants.PAYMENT_INVALID_BODY, CreatePaymentDto.class);

        doThrow(new PaymentException400(PaymentConstants.ERROR_INVALID_BODY))
                .when(paymentMock).doPayment(createPaymentInvalidDto, null);

        return paymentMock;
    }
}
