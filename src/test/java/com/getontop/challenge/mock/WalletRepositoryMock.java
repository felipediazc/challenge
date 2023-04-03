package com.getontop.challenge.mock;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.repository.WalletRepository;
import com.getontop.challenge.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class WalletRepositoryMock {

    @Bean
    WalletRepository walletRepositoryComponent() {
        Account account = Constants.setAccount(1, "ONTOP INC", "0245253419",
                "028444018");
        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setName("Angelica");
        wallet.setLastname("Rivera C");
        wallet.setRoutingnumber("R2");
        wallet.setAccountnumber("A2");
        wallet.setNationalidnumber("N2");
        wallet.setBankname("MY BANK");
        wallet.setAccountid(account);
        WalletRepository walletRepository = mock(WalletRepository.class);
        when(walletRepository.findById(1)).thenReturn(Optional.of(wallet));
        return walletRepository;
    }
}
