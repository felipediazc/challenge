package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.dto.*;
import com.getontop.challenge.exception.PaymentException;
import com.getontop.challenge.port.PaymentData;
import com.getontop.challenge.port.PaymentProvider;
import com.getontop.challenge.util.PaymentConstants;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Payment {
    private final String PAYLOAD = """
                        {
                "source": {
                    "type": "COMPANY",
                    "sourceInformation": {
                        "name": "ONTOP INC"
                    },
                    "account": {
                        "accountNumber": "0245253419",
                        "currency": "USD",
                        "routingNumber": "028444018"
                    }
                },
                "destination": {
                    "name": "TONY STARK",
                    "account": {
                        "accountNumber": "1885226711",
                        "currency": "USD",
                        "routingNumber": "211927207"
                    }
                },
                "amount": 1000
            }
            """;
    private final PaymentProvider paymentProvider;
    private final PaymentData paymentData;

    public Payment(final PaymentProvider paymentProvider, PaymentData paymentData) {
        this.paymentProvider = paymentProvider;
        this.paymentData = paymentData;
    }

    public CreatePaymentResponseDto doPayment(Integer accountId, Integer accountDestinationId, Double amount, CurrencyEnum currency) {

        Optional<Account> accountOptional = paymentData.getAccountById(accountId);
        if (accountOptional.isEmpty()) {
            throw new PaymentException(PaymentConstants.ERROR_INVALID_ACCOUNT_ID);
        }
        Optional<Accountdestination> accountdestinationOptional = paymentData.getAccountDestinationById(accountDestinationId);
        if (accountdestinationOptional.isEmpty()) {
            throw new PaymentException(PaymentConstants.ERROR_INVALID_ACCOUNT_DESTINATION_ID);
        }
        CreatePaymentDto createPaymentDto = new CreatePaymentDto();
        SourceDto sourceDto = getSourceDto(accountOptional.get(), currency);
        createPaymentDto.setSource(sourceDto);
        DestinationDto destinationDto = getDestinationDto(accountdestinationOptional.get(), currency);
        createPaymentDto.setDestination(destinationDto);
        createPaymentDto.setAmount(amount);

        CreatePaymentResponseDto createPaymentResponseDto = paymentProvider.doPayment(createPaymentDto);
        /* falata meter en la base de datos*/
        /*falta agregar en la tabla de transacciones localtransactionid que guarda el record local*/
        return createPaymentResponseDto;
    }

    private SourceDto getSourceDto(Account account, CurrencyEnum currency){
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

    private DestinationDto getDestinationDto(Accountdestination accountdestination, CurrencyEnum currency){
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
}
