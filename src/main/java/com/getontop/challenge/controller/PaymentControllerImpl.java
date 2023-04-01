package com.getontop.challenge.controller;

import com.getontop.challenge.domain.Payment;


import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.port.PaymentController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentControllerImpl implements PaymentController {


    private final Payment payment;

    public PaymentControllerImpl(final Payment payment) {
        this.payment = payment;
    }

    @Override
    public CreatePaymentResponseDto doPayment(PaymentPayloadDto paymentPayloadDto) {
        return payment.doPayment(paymentPayloadDto.getAccountId(), paymentPayloadDto.getAccountDestinationId(), paymentPayloadDto.getAmount());
    }
}
