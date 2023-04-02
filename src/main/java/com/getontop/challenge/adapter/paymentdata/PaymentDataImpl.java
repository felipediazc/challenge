package com.getontop.challenge.adapter.paymentdata;

import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.AccountdestinationService;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.port.PaymentData;
import org.springframework.stereotype.Service;

@Service
public class PaymentDataImpl implements PaymentData {

    private final AccountService accountService;
    private final AccountdestinationService accountdestinationService;

    public PaymentDataImpl(AccountService accountService, AccountdestinationService accountdestinationService) {
        this.accountService = accountService;
        this.accountdestinationService = accountdestinationService;
    }

    @Override
    public void setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus) {

accountService.getAccountById(paymentPayloadDto.getAccountId());
accountdestinationService.getAccountDestinationById(paymentPayloadDto.getAccountDestinationId());
    }
}
