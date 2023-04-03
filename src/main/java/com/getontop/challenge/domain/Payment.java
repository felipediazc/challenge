package com.getontop.challenge.domain;

import com.getontop.challenge.adapter.paymentdata.PaymentStatus;
import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.dto.*;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.port.PaymentData;
import com.getontop.challenge.port.PaymentProvider;
import com.getontop.challenge.util.PaymentConstants;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class Payment {

    private final PaymentProvider paymentProvider;
    private final PaymentData paymentData;

    public Payment(final PaymentProvider paymentProvider, PaymentData paymentData) {
        this.paymentProvider = paymentProvider;
        this.paymentData = paymentData;
    }

    public CreatePaymentResponseDto doPayment(Integer accountId, Integer accountDestinationId, Double amount, CurrencyEnum currency) {

        Optional<Account> accountOptional = paymentData.getAccountById(accountId);
        if (accountOptional.isEmpty()) {
            throw new PaymentException400(getPaymentExceptionErrorMsg(PaymentConstants.ERROR_INVALID_ACCOUNT_ID, accountId));
        }
        Optional<Accountdestination> accountdestinationOptional = paymentData.getAccountDestinationById(accountDestinationId);
        if (accountdestinationOptional.isEmpty()) {
            throw new PaymentException400(getPaymentExceptionErrorMsg(PaymentConstants.ERROR_INVALID_ACCOUNT_DESTINATION_ID, accountDestinationId));
        }
        UUID localTransactionId = UUID.randomUUID();
        CreatePaymentDto createPaymentDto = new CreatePaymentDto();
        SourceDto sourceDto = getSourceDto(accountOptional.get(), currency);
        createPaymentDto.setSource(sourceDto);
        DestinationDto destinationDto = getDestinationDto(accountdestinationOptional.get(), currency);
        createPaymentDto.setDestination(destinationDto);
        createPaymentDto.setAmount(amount);

        CreatePaymentResponseDto createPaymentResponseDto = paymentProvider.doPayment(createPaymentDto, localTransactionId);
        if (createPaymentResponseDto.getRequestInfo().getStatus().equalsIgnoreCase(PaymentConstants.ENDPOINT_PROCESSING_STATUS_STRING)) {
            PaymentPayloadDto paymentPayloadDto = new PaymentPayloadDto();
            paymentPayloadDto.setCurrency(currency);
            paymentPayloadDto.setAccountId(accountId);
            paymentPayloadDto.setAccountDestinationId(accountDestinationId);
            paymentPayloadDto.setAmount(amount);
            String peerTransactionId = createPaymentResponseDto.getPaymentInfo().getId();
            paymentData.setTransaction(paymentPayloadDto, PaymentStatus.IN_PROGRESS, String description, peerTransactionId, localTransactionId.toString());
        }
        return createPaymentResponseDto;
    }

    private SourceDto getSourceDto(Account account, CurrencyEnum currency) {
        SourceDto sourceDto = new SourceDto();
        sourceDto.setType(SourceTypeEnum.COMPANY);
        SourceInformationDto sourceInformationDto = new SourceInformationDto();
        sourceInformationDto.setName(PaymentConstants.DEFAULT_COMPANY_NAME);
        sourceDto.setSourceInformation(sourceInformationDto);
        AccountDto accountSourceDto = new AccountDto();
        accountSourceDto.setAccountNumber(account.getAccountnumber());
        accountSourceDto.setRoutingNumber(account.getRoutingnumber());
        accountSourceDto.setCurrency(currency);
        sourceDto.setAccount(accountSourceDto);
        return sourceDto;
    }

    private DestinationDto getDestinationDto(Accountdestination accountdestination, CurrencyEnum currency) {
        DestinationDto destinationDto = new DestinationDto();
        AccountDto accountDestinationDto = new AccountDto();
        accountDestinationDto.setCurrency(currency);
        accountDestinationDto.setAccountNumber(accountdestination.getAccountnumber());
        accountDestinationDto.setRoutingNumber(accountdestination.getRoutingnumber());
        destinationDto.setAccount(accountDestinationDto);
        StringBuilder name = new StringBuilder(accountdestination.getName()).append(" ").append(accountdestination.getLastname());
        destinationDto.setName(name.toString());
        return destinationDto;
    }

    private String getPaymentExceptionErrorMsg(String mainMsg, Integer accountId) {
        return new StringBuilder(mainMsg).append(": ").append(accountId).toString();
    }
}
