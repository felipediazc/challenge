package com.getontop.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentInfoDto {
    @Schema(description = "Amount transfered",
            name = "amount",
            example = "20.0")
    private Double amount;
    @Schema(description = "Remote transaction Id",
            name = "id",
            example = "e8ca0fa8-0c01-48d2-99bb-bcff154034c2")
    private String id;
}
