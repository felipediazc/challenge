package com.getontop.challenge.adapter.ontopdata;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.WalletService;
import com.getontop.challenge.domain.TransactionService;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.port.OnTopData;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class OnTopDataImpl implements OnTopData {

    private final AccountService accountService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    public OnTopDataImpl(AccountService accountService, WalletService walletService, TransactionService transactionService) {
        this.accountService = accountService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @Override
    public Optional<Account> getAccountById(Integer accountId) {
        return accountService.getAccountById(accountId);
    }

    @Override
    public Optional<Wallet> getWalletById(Integer walletId) {
        return walletService.getWalletById(walletId);
    }

    @Override
    public Transaction setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus, String description, String peerTransactionId,
                                      String localTransactionId) {
        Transaction transaction = new Transaction();
        transaction.setAccountid(paymentPayloadDto.getAccountId());
        transaction.setWalletid(paymentPayloadDto.getWalletId());
        transaction.setAmount(paymentPayloadDto.getAmount());
        transaction.setStatus(paymentStatus.toString());
        transaction.setDescription(description);
        transaction.setTransactiondate(Instant.now());
        transaction.setPeertransactionid(peerTransactionId);
        transaction.setLocaltransactionid(localTransactionId);
        return transactionService.save(transaction);
    }
}
