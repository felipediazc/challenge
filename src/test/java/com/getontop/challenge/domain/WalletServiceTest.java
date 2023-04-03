package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.mock.WalletRepositoryMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {WalletRepositoryMock.class, WalletService.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Test
    void getWalletById() {
        Optional<Wallet> walletOptional = walletService.getWalletById(1);
        assertTrue(walletOptional.isPresent());
        assertEquals(1, walletOptional.get().getId());
    }
}