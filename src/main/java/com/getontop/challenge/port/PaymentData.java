package com.getontop.challenge.port;


import com.getontop.challenge.adapter.paymentdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.db.entity.Accounttransaction;
import com.getontop.challenge.dto.PaymentPayloadDto;

import java.util.Optional;

public interface PaymentData {

    Accounttransaction setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus, String description, String peerTransactionId,
                                      String localTransactionId);

    Optional<Account> getAccountById(Integer accountId);

    Optional<Accountdestination> getAccountDestinationById(Integer accountDestinationId);

}
