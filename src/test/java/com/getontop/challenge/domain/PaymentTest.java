package com.getontop.challenge.domain;

import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.CurrencyEnum;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.mock.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = {AccountRepositoryMock.class, AccountService.class,
        WalletService.class, WalletRepositoryMock.class,
        TransactionService.class, TransactionRepositoryMock.class,
        OntopcommissionService.class, OntopcommissionRepositoryMock.class,
        OnTopDataMock.class, Payment.class,
        ExternalEndpointIntegrationMock.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class PaymentTest {
    @Autowired
    Payment payment;

    @Test
    void doPayment() {
        CreatePaymentResponseDto createPaymentResponseDto = payment.doPayment(1, 1, 20.0, CurrencyEnum.USD);
        assertEquals("Processing", createPaymentResponseDto.getRequestInfo().getStatus());
    }

    @Test
    void testPaymentExceptions() {
        assertThrows(PaymentException400.class, () -> {
            payment.doPayment(1, 1, 0.0, CurrencyEnum.USD);
        });
        assertThrows(PaymentException400.class, () -> {
            payment.doPayment(3, 1, 10.0, CurrencyEnum.USD);
        });
        assertThrows(PaymentException400.class, () -> {
            payment.doPayment(1, 5, 12000.0, CurrencyEnum.USD);
        });
        assertThrows(PaymentException400.class, () -> {
            payment.doPayment(1, 6, 12000.0, CurrencyEnum.USD);
        });
    }
}