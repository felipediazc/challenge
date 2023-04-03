package com.getontop.challenge.port;


import com.getontop.challenge.adapter.ontopdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.dto.PaymentPayloadDto;

import java.util.Optional;

public interface OnTopData {

    Transaction setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus, String description, String peerTransactionId,
                               String localTransactionId);

    Optional<Account> getAccountById(Integer accountId);

    Optional<Wallet> getWalletById(Integer walletId);

}
