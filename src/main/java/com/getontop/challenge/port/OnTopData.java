package com.getontop.challenge.port;


import com.getontop.challenge.adapter.ontopdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface OnTopData {

    public Transaction setTransaction(Account account, Wallet wallet, Double amount,
                                      PaymentStatus paymentStatus, String description,
                                      String peerTransactionId, UUID localTransactionId);

    Optional<Account> getAccountById(Integer accountId);

    Optional<Wallet> getWalletById(Integer walletId);

}
