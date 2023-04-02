package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Accounttransaction;
import com.getontop.challenge.db.repository.AccounttransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class AccounttransactionService {

    private final AccounttransactionRepository accounttransactionRepository;

    public AccounttransactionService(AccounttransactionRepository accounttransactionRepository) {

        this.accounttransactionRepository = accounttransactionRepository;
    }

    public Accounttransaction save(Accounttransaction accounttransaction) {
        return accounttransactionRepository.save(accounttransaction);
    }
}
