package com.getontop.challenge.port;

import com.getontop.challenge.dto.*;

import java.util.UUID;

public interface ExternalEndpointIntegration {

    CreatePaymentResponseDto doPayment(final CreatePaymentDto createPaymentDto, final UUID localTransactionId);
    BalanceResponseDto getBalance(Integer walletId);
    WalletResponseDto updateWallet(WalletPayloadDto walletPayloadDto, final UUID localTransactionId);

}
