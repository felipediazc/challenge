package com.getontop.challenge.adapter;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.port.Payment;

public class DefaultPaymentImpl implements Payment {
    @Override
    public CreatePaymentResponseDto doPayment(CreatePaymentDto createPaymentDto) {
        return null;
    }
}
