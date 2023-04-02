package com.getontop.challenge.adapter.paymentcontroller;

import com.getontop.challenge.domain.Payment;


import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.port.PaymentController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentControllerImpl implements PaymentController {


    private final Payment payment;

    public PaymentControllerImpl(final Payment payment) {
        this.payment = payment;
    }

    @Override
    public CreatePaymentResponseDto doPayment(PaymentPayloadDto paymentPayloadDto) {
        log.info("Getting payment request with date {}", paymentPayloadDto);
        return payment.doPayment(paymentPayloadDto.getAccountId(), paymentPayloadDto.getAccountDestinationId(), paymentPayloadDto.getAmount(), paymentPayloadDto.getCurrency());
    }
}
