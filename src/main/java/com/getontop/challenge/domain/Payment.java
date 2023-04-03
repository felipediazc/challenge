package com.getontop.challenge.domain;

import com.getontop.challenge.adapter.ontopdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.dto.*;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.port.OnTopData;
import com.getontop.challenge.port.ExternalEndpointIntegration;
import com.getontop.challenge.util.PaymentConstants;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class Payment {

    private final ExternalEndpointIntegration externalEndpointIntegration;
    private final OnTopData onTopData;

    public Payment(final ExternalEndpointIntegration externalEndpointIntegration, OnTopData onTopData) {
        this.externalEndpointIntegration = externalEndpointIntegration;
        this.onTopData = onTopData;
    }

    public CreatePaymentResponseDto doPayment(Integer accountId, Integer walletId, Double amount, CurrencyEnum currency) {
        Double transactionFee = 0.0;
        Optional<Account> accountOptional = onTopData.getAccountById(accountId);
        if (accountOptional.isEmpty()) {
            throw new PaymentException400(getPaymentExceptionErrorMsg(PaymentConstants.ERROR_INVALID_ACCOUNT_ID, accountId));
        }
        Optional<Wallet> walletOptional = onTopData.getWalletById(walletId);
        if (walletOptional.isEmpty()) {
            throw new PaymentException400(getPaymentExceptionErrorMsg(PaymentConstants.ERROR_INVALID_ACCOUNT_DESTINATION_ID, walletId));
        }
        BalanceResponseDto balanceResponseDto = externalEndpointIntegration.getBalance(walletId);
        if (balanceResponseDto.getBalance() >= amount) {
            transactionFee = getTransactionFee(amount);
            amount = amount - transactionFee;
        } else {
            throw new PaymentException400(getPaymentExceptionErrorMsg(PaymentConstants.ERROR_NO_SUFFICIENT_FUNDS, accountId));
        }
        UUID localTransactionId = UUID.randomUUID();
        CreatePaymentDto createPaymentDto = new CreatePaymentDto();
        SourceDto sourceDto = getSourceDto(accountOptional.get(), currency);
        createPaymentDto.setSource(sourceDto);
        DestinationDto destinationDto = getDestinationDto(walletOptional.get(), currency);
        createPaymentDto.setDestination(destinationDto);
        createPaymentDto.setAmount(amount);

        CreatePaymentResponseDto createPaymentResponseDto = externalEndpointIntegration.doPayment(createPaymentDto, localTransactionId);
        if (createPaymentResponseDto.getRequestInfo().getStatus().equalsIgnoreCase(PaymentConstants.ENDPOINT_PROCESSING_STATUS_STRING)) {
            PaymentPayloadDto paymentPayloadDto = new PaymentPayloadDto();
            paymentPayloadDto.setCurrency(currency);
            paymentPayloadDto.setAccountId(accountId);
            paymentPayloadDto.setWalletId(walletId);
            paymentPayloadDto.setAmount(amount);
            String peerTransactionId = createPaymentResponseDto.getPaymentInfo().getId();
            onTopData.setTransaction(paymentPayloadDto, PaymentStatus.IN_PROGRESS, "String description", peerTransactionId, localTransactionId.toString());
        }

        WalletPayloadDto walletPayloadDto = new WalletPayloadDto((-1 * amount), walletId);
        externalEndpointIntegration.updateWallet(walletPayloadDto, localTransactionId);
        return createPaymentResponseDto;
    }

    private SourceDto getSourceDto(Account account, CurrencyEnum currency) {
        SourceDto sourceDto = new SourceDto();
        sourceDto.setType(SourceTypeEnum.COMPANY);
        SourceInformationDto sourceInformationDto = new SourceInformationDto();
        sourceInformationDto.setName(account.getName());
        sourceDto.setSourceInformation(sourceInformationDto);
        AccountDto accountSourceDto = new AccountDto();
        accountSourceDto.setAccountNumber(account.getAccountnumber());
        accountSourceDto.setRoutingNumber(account.getRoutingnumber());
        accountSourceDto.setCurrency(currency);
        sourceDto.setAccount(accountSourceDto);
        return sourceDto;
    }

    private DestinationDto getDestinationDto(Wallet wallet, CurrencyEnum currency) {
        DestinationDto destinationDto = new DestinationDto();
        AccountDto accountDto = new AccountDto();
        accountDto.setCurrency(currency);
        accountDto.setAccountNumber(wallet.getAccountnumber());
        accountDto.setRoutingNumber(wallet.getRoutingnumber());
        destinationDto.setAccount(accountDto);
        StringBuilder name = new StringBuilder(wallet.getName()).append(" ").append(wallet.getLastname());
        destinationDto.setName(name.toString());
        return destinationDto;
    }

    private String getPaymentExceptionErrorMsg(String mainMsg, Integer accountId) {
        return new StringBuilder(mainMsg).append(": ").append(accountId).toString();
    }

    private Double getTransactionFee(Double amount) {
        return (amount * 0.1);
    }
}
