package com.getontop.challenge.port;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;

public interface Payment {

    CreatePaymentResponseDto doPayment(final CreatePaymentDto createPaymentDto);

}
