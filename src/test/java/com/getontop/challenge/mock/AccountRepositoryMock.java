package com.getontop.challenge.mock;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class AccountRepositoryMock {

    @Bean
    AccountRepository accountRepositoryComponent(){
        Account account = new Account();
        account.setId(1);
        account.setName("Felipe");
        account.setRoutingnumber("R1");
        account.setAccountnumber("A1");
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        return accountRepository;
    }
}
