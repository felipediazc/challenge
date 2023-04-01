package com.getontop.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SourceDto {
    private SourceTypeEnum type;
    private SourceInformationDto sourceInformation;
    private AccountDto account;
}
