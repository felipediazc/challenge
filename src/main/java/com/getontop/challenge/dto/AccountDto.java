package com.getontop.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {
    private String accountNumber;
    private CurrencyEnum currency;
    private String routingNumber;
}
