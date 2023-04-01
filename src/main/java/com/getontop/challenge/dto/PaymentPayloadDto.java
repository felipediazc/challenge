package com.getontop.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPayloadDto {
    Integer accountId;
    Integer accountDestinationId;
    Double amount;
}
