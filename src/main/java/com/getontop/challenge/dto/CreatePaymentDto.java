package com.getontop.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePaymentDto {
    private SourceDto source;
    private DestinationDto destination;
    private Double amount;
}
