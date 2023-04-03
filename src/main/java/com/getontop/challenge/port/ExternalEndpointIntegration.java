package com.getontop.challenge.port;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;

import java.util.UUID;

public interface ExternalEndpointIntegration {

    CreatePaymentResponseDto doPayment(final CreatePaymentDto createPaymentDto, final UUID localTransactionId);

}
