package com.getontop.challenge.mock;

import com.getontop.challenge.adapter.ontopdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.exception.PaymentException500;
import com.getontop.challenge.port.OnTopData;
import com.getontop.challenge.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class OnTopDataMock {

    @Bean
    OnTopData onTopDataComponent() {
        Account account = Constants.setAccount(1, "ONTOP INC", "0245253419",
                "028444018");
        Wallet wallet = Constants.setWallet(1, "TONY", "STARK", "1885226711",
                "211927207", "1111111111", account, "BANK1");
        Transaction transaction = Constants.setTransaction(account, wallet);
        OnTopData onTopData = mock(OnTopData.class);
        when(onTopData.getAccountById(1)).thenReturn(Optional.of(account));
        when(onTopData.getWalletById(1)).thenReturn(Optional.of(wallet));
        when(onTopData.getWalletById(5)).thenReturn(Optional.empty());
        when(onTopData.getWalletById(6)).thenReturn(Optional.of(wallet));
        when(onTopData.setTransaction(any(), any(), eq(20.0), eq(PaymentStatus.COMPLETED), eq(""),
                eq("PT1"), any())).thenReturn(transaction);
        doThrow(new PaymentException500("database error"))
                .when(onTopData).setTransaction(any(), any(), eq(20.0), eq(PaymentStatus.COMPLETED), eq(""),
                eq("PT2"), any());
        return onTopData;
    }
}
