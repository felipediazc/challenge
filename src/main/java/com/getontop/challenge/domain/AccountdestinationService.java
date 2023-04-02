package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.db.repository.AccountdestinationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountdestinationService {

    private final AccountdestinationRepository accountdestinationRepository;

    public AccountdestinationService(AccountdestinationRepository accountdestinationRepository) {
        this.accountdestinationRepository = accountdestinationRepository;
    }

    public Optional<Accountdestination> getAccountDestinationById(Integer accountDestinationId) {
        return accountdestinationRepository.findById(accountDestinationId);
    }
}
