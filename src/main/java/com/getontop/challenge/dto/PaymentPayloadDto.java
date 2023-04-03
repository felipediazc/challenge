package com.getontop.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPayloadDto {
    @Schema(description = "ON TOP Bank Account ID.",
            name = "accountId",
            example = "1")
    Integer accountId;
    @Schema(description = "Wallet ID / UserID (are the same)",
            name = "walletId",
            example = "1")
    Integer walletId;
    @Schema(description = "Amount to be transferred",
            name = "amount",
            example = "20.0")
    Double amount;
    @Schema(description = "Amount currency",
            name = "currency",
            example = "USD")
    CurrencyEnum currency;
}
