package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.mock.AccountdestinationRepositoryMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AccountdestinationRepositoryMock.class, AccountdestinationService.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class AccountdestinationServiceTest {

    @Autowired
    AccountdestinationService accountdestinationService;

    @Test
    void getAccountDestinationById() {
        Optional<Accountdestination> accountdestinationOptional = accountdestinationService.getAccountDestinationById(1);
        assertTrue(accountdestinationOptional.isPresent());
        assertEquals(1, accountdestinationOptional.get().getId());
    }
}