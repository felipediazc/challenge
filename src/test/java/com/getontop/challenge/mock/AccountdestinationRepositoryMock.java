package com.getontop.challenge.mock;

import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.db.repository.AccountdestinationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class AccountdestinationRepositoryMock {

    @Bean
    AccountdestinationRepository accountDestinationRepositoryComponent(){
        Accountdestination accountdestination = new Accountdestination();
        accountdestination.setId(1);
        accountdestination.setName("Angelica");
        accountdestination.setLastname("Rivera C");
        accountdestination.setRoutingnumber("R2");
        accountdestination.setAccountnumber("A2");
        accountdestination.setNationalnumber("N2");
        accountdestination.setAccountid(1);
        AccountdestinationRepository accountdestinationRepository = mock(AccountdestinationRepository.class);
        when(accountdestinationRepository.findById(1)).thenReturn(Optional.of(accountdestination));
        return accountdestinationRepository;
    }
}
