package com.getontop.challenge.mock;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.WalletPayloadDto;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.port.ExternalEndpointIntegration;
import com.getontop.challenge.util.Constants;
import com.getontop.challenge.util.PaymentConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;


import java.util.UUID;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ExternalEndpointIntegrationMock {

    @Bean
    public ExternalEndpointIntegration paymentProviderComponent() {
        ExternalEndpointIntegration paymentMock = mock(ExternalEndpointIntegration.class);
        Gson gson = new GsonBuilder().create();
        CreatePaymentDto createPaymentSuccessDto = gson.fromJson(Constants.PAYMENT_SUCCESS_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentSuccessResponseDto = gson.fromJson(Constants.PAYMENT_SUCCESS_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentDto createPaymentRejectedDto = gson.fromJson(Constants.PAYMENT_REJECTED_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentRejectedResponseDto = gson.fromJson(Constants.PAYMENT_REJECTED_RESPONSE, CreatePaymentResponseDto.class);
        CreatePaymentDto createPaymentTimeoutDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentTimeoutResponseDto = gson.fromJson(Constants.PAYMENT_TIMEOUT_RESPONSE, CreatePaymentResponseDto.class);


        CreatePaymentDto createPaymentSuccessFullTestDto = gson.fromJson(Constants.PAYMENT_SUCCESS_FULL_TEST_RESPONSE, CreatePaymentDto.class);
        when(paymentMock.doPayment(eq(createPaymentSuccessFullTestDto), any(UUID.class))).thenReturn(createPaymentSuccessResponseDto);
        when(paymentMock.doPayment(createPaymentSuccessDto, null)).thenReturn(createPaymentSuccessResponseDto);
        when(paymentMock.doPayment(createPaymentRejectedDto, null)).thenReturn(createPaymentRejectedResponseDto);
        when(paymentMock.doPayment(createPaymentTimeoutDto, null)).thenReturn(createPaymentTimeoutResponseDto);
        when(paymentMock.getBalance(1)).thenReturn(Constants.getPositiveBalanceDto());
        when(paymentMock.getBalance(6)).thenReturn(Constants.getPositiveBalanceDto());
        WalletPayloadDto walletPayloadDto = gson.fromJson(Constants.WALLET_GOOD_PAYLOAD, WalletPayloadDto.class);
        when(paymentMock.updateWallet(walletPayloadDto,null)).thenReturn(Constants.getPositiveWalletResponse());
        /*Exception handling*/
        CreatePaymentDto createPaymentInvalidDto = gson.fromJson(Constants.PAYMENT_INVALID_BODY, CreatePaymentDto.class);

        doThrow(new PaymentException400(PaymentConstants.ERROR_INVALID_BODY))
                .when(paymentMock).doPayment(createPaymentInvalidDto, null);

        return paymentMock;
    }
}
