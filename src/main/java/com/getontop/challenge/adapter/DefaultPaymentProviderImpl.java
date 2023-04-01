package com.getontop.challenge.adapter;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.port.PaymentProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DefaultPaymentProviderImpl implements PaymentProvider {


    @Value("${paymentEndpoint}")
    private String paymentEndpoint;

    @Override
    public CreatePaymentResponseDto doPayment(CreatePaymentDto createPaymentDto) {

        CreatePaymentResponseDto createPaymentResponseDto = WebClient.create(paymentEndpoint).post()
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createPaymentDto), CreatePaymentDto.class)
                .retrieve()
                .bodyToMono(CreatePaymentResponseDto.class)
                .block();
        return createPaymentResponseDto;
    }
}
