package com.getontop.challenge.adapter.paymentdata;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.db.entity.Accounttransaction;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.AccountdestinationService;
import com.getontop.challenge.domain.AccounttransactionService;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.port.PaymentData;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PaymentDataImpl implements PaymentData {

    private final AccountService accountService;
    private final AccountdestinationService accountdestinationService;
    private final AccounttransactionService accounttransactionService;

    public PaymentDataImpl(AccountService accountService, AccountdestinationService accountdestinationService, AccounttransactionService accounttransactionService) {
        this.accountService = accountService;
        this.accountdestinationService = accountdestinationService;
        this.accounttransactionService = accounttransactionService;
    }

    @Override
    public Optional<Account> getAccountById(Integer accountId) {
        return accountService.getAccountById(accountId);
    }

    @Override
    public Optional<Accountdestination> getAccountDestinationById(Integer accountDestinationId) {
        return accountdestinationService.getAccountDestinationById(accountDestinationId);
    }

    @Override
    public Accounttransaction setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus, String description, String transactionId) {
        Accounttransaction accounttransaction = new Accounttransaction();
        accounttransaction.setAccountid(paymentPayloadDto.getAccountId());
        accounttransaction.setAmount(paymentPayloadDto.getAmount());
        accounttransaction.setStatus(paymentStatus.toString());
        accounttransaction.setDescription(description);
        accounttransaction.setTransactiondate(Instant.now());
        accounttransaction.setTransactionid(transactionId);
        return accounttransactionService.save(accounttransaction);
    }
}
