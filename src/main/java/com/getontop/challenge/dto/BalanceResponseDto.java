package com.getontop.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BalanceResponseDto {
    private Double balance;
    @JsonProperty("user_id")
    private Integer walletId;
}
