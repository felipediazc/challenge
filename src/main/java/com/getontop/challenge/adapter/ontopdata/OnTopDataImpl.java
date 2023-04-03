package com.getontop.challenge.adapter.ontopdata;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Ontopcommission;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.OntopcommissionService;
import com.getontop.challenge.domain.WalletService;
import com.getontop.challenge.domain.TransactionService;
import com.getontop.challenge.exception.PaymentException500;
import com.getontop.challenge.port.OnTopData;
import com.getontop.challenge.util.PaymentConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class OnTopDataImpl implements OnTopData {

    private final AccountService accountService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    private final OntopcommissionService ontopcommissionService;

    public OnTopDataImpl(AccountService accountService, WalletService walletService,
                         TransactionService transactionService, OntopcommissionService ontopcommissionService) {
        this.accountService = accountService;
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.ontopcommissionService = ontopcommissionService;
    }

    @Override
    public Optional<Account> getAccountById(Integer accountId) {
        return accountService.getAccountById(accountId);
    }

    @Override
    public Optional<Wallet> getWalletById(Integer walletId) {
        return walletService.getWalletById(walletId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Transaction setTransaction(Account account, Wallet wallet, Double amount,
                                      PaymentStatus paymentStatus, String description,
                                      String peerTransactionId, UUID localTransactionId) {

        try {
            Double originalAmount = amount;
            Double transactionFee = PaymentConstants.getTransactionFee(amount);
            amount = amount - transactionFee;

            Transaction transaction = new Transaction();
            transaction.setAccountid(account);
            transaction.setWalletid(wallet);
            transaction.setAmount(amount);
            transaction.setStatus(paymentStatus.toString());
            transaction.setDescription(description);
            transaction.setTransactiondate(Instant.now());
            transaction.setPeertransactionid(peerTransactionId);
            transaction.setLocaltransactionid(localTransactionId.toString());
            transaction = transactionService.save(transaction);

            Ontopcommission ontopcommission = new Ontopcommission();
            ontopcommission.setAmount(originalAmount);
            ontopcommission.setStatus(paymentStatus.toString());
            ontopcommission.setComission(transactionFee);
            ontopcommission.setCommissiondate(Instant.now());
            ontopcommission.setTransactionid(transaction);
            ontopcommission.setLocaltransactionid(localTransactionId.toString());
            ontopcommission.setPeertransactionid(peerTransactionId);
            return transaction;
        } catch (Exception e) {
            throw new PaymentException500("Error on data persistence " + e.getMessage(), localTransactionId);
        }
    }

}
