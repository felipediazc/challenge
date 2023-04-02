package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.mock.AccountRepositoryMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AccountRepositoryMock.class, AccountService.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    void getAccountById() {
        Optional<Account> accountOptional = accountService.getAccountById(1);
        assertTrue(accountOptional.isPresent());
        assertEquals(1, accountOptional.get().getId());
    }
}