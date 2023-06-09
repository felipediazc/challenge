package com.getontop.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePaymentResponseDto {
    private RequestInfoDto requestInfo;
    private PaymentInfoDto paymentInfo;
}
