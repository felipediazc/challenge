package com.getontop.challenge.adapter.ontopdata;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.OntopcommissionService;
import com.getontop.challenge.domain.TransactionService;
import com.getontop.challenge.domain.WalletService;
import com.getontop.challenge.mock.*;
import com.getontop.challenge.port.OnTopData;
import com.getontop.challenge.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AccountRepositoryMock.class, AccountService.class,
        WalletService.class, WalletRepositoryMock.class,
        TransactionService.class, TransactionRepositoryMock.class,
        OntopcommissionService.class, OntopcommissionRepositoryMock.class,
        OnTopDataImpl.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class OnTopDataImplTest {

    @Autowired
    AccountService accountService;
    @Autowired
    WalletService walletService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    OntopcommissionService ontopcommissionService;
    @Autowired
    OnTopData onTopData;

    @Test
    void getAccountById() {
        Optional<Account> accountOptional = onTopData.getAccountById(1);
        assertTrue(accountOptional.isPresent());
        assertEquals(1, accountOptional.get().getId());
    }

    @Test
    void getWalletById() {
        Optional<Wallet> walletOptional = onTopData.getWalletById(1);
        assertTrue(walletOptional.isPresent());
        assertEquals(1, walletOptional.get().getId());
    }

    @Test
    void setTransaction() {
        Account account = Constants.setAccount(1, "ONTOP INC", "0245253419",
                "028444018");
        Wallet wallet = Constants.setWallet(1, "TONY", "STARK", "1885226711",
                "211927207", "1111111111", account, "BANK1");
        UUID localTransactionId = UUID.randomUUID();
        Transaction transaction = onTopData.setTransaction(account, wallet, 20.0, PaymentStatus.COMPLETED,
                "", "PT1", localTransactionId);
        assertEquals(1, transaction.getId());
    }

}