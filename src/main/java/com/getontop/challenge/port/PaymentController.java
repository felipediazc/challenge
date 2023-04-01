package com.getontop.challenge.port;

import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.PaymentPayloadDto;
import org.springframework.web.bind.annotation.PostMapping;

public interface PaymentController {
    @PostMapping("/api/v1/payments")
    CreatePaymentResponseDto doPayment(PaymentPayloadDto paymentPayloadDto);
}
