package com.getontop.challenge.mock;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.repository.TransactionRepository;
import com.getontop.challenge.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class TransactionRepositoryMock {

    @Bean
    TransactionRepository transactionRepositoryComponent() {
        Account account = Constants.setAccount(1, "ONTOP INC", "0245253419",
                "028444018");
        Wallet wallet = Constants.setWallet(1, "TONY", "STARK", "1885226711",
                "211927207", "1111111111", account, "BANK1");

        Transaction transaction = Constants.setTransaction(account, wallet);
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        when(transactionRepository.save(any())).thenReturn(transaction);
        return transactionRepository;
    }
}
