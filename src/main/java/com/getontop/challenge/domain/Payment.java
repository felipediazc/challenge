package com.getontop.challenge.domain;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.port.PaymentProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

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
    public Payment(final PaymentProvider paymentProvider){
        this.paymentProvider = paymentProvider;
    }

    public CreatePaymentResponseDto doPayment(Integer accountId, Integer accountDestinationId, Double amount){
        CreatePaymentDto createPaymentDto;
        Gson gson = new GsonBuilder().create();
        createPaymentDto = gson.fromJson(PAYLOAD, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentResponseDto = paymentProvider.doPayment(createPaymentDto);
        return createPaymentResponseDto;
    }
}
