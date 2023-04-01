package com.getontop.challenge.port;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;

public interface PaymentProvider {

    CreatePaymentResponseDto doPayment(final CreatePaymentDto createPaymentDto);

}
