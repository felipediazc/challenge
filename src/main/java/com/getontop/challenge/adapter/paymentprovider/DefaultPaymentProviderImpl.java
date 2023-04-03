package com.getontop.challenge.adapter.paymentprovider;

import com.getontop.challenge.dto.CreatePaymentDto;
import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.CreatePaymentResponseErrorDto;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.exception.PaymentException500;
import com.getontop.challenge.port.PaymentProvider;
import com.google.gson.Gson;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class DefaultPaymentProviderImpl implements PaymentProvider {


    @Value("${paymentEndpoint}")
    private String paymentEndpoint;

    @Value("${webClientTimeout:15000}")
    private Integer webClientTimeout;

    @Value("${webClientRetry:1}")
    private Integer webClientRetry;

    private final Integer timeBetweenRetry = 2;

    @Override
    public CreatePaymentResponseDto doPayment(CreatePaymentDto createPaymentDto, final UUID localTransactionId) {
        log.info("Trying to send data to the endpoint. payload is: {}, localTransactionId: {}", createPaymentDto, localTransactionId);
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientTimeout)
                .responseTimeout(Duration.ofMillis(webClientTimeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(webClientTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(webClientTimeout, TimeUnit.MILLISECONDS)));

        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(paymentEndpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Mono<CreatePaymentResponseDto> createPaymentResponseDtoMono = client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createPaymentDto), CreatePaymentDto.class)
                .retrieve()
                .onStatus(httpStatusCode ->
                        httpStatusCode.is4xxClientError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException400(getErrorMessage(errorBody), localTransactionId)))
                )
                .onStatus(httpStatusCode ->
                        httpStatusCode.is5xxServerError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException500(getErrorMessage(errorBody), localTransactionId)))
                )
                .bodyToMono(CreatePaymentResponseDto.class)
                .retryWhen(Retry.fixedDelay(webClientRetry, Duration.ofSeconds(timeBetweenRetry))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException)
                        .doBeforeRetry(r -> log.warn("**** Retry {}", r))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new PaymentException500(retrySignal.failure().getMessage(), localTransactionId)));
        CreatePaymentResponseDto createPaymentResponseDto = createPaymentResponseDtoMono.block();
        log.info("Info has been sent to the endpoint. payload was: {}, response was: {}. localTransactionId: {}",
                createPaymentDto, createPaymentResponseDto, localTransactionId);
        return createPaymentResponseDto;
    }

    private String getErrorMessage(String message) {
        Gson gson = new Gson();
        try {
            CreatePaymentResponseDto createPaymentResponseDto = gson.fromJson(message, CreatePaymentResponseDto.class);
            String status = createPaymentResponseDto.getRequestInfo().getStatus();
            String error = createPaymentResponseDto.getRequestInfo().getError();
            return new StringBuilder("Status: ").append(status).append(". ").append(error).toString();
        } catch (Exception e1) {
            try {
                CreatePaymentResponseErrorDto createPaymentResponseErrorDto = gson.fromJson(message, CreatePaymentResponseErrorDto.class);
                return createPaymentResponseErrorDto.getError();
            } catch (Exception e2) {
                return message;
            }
        }
    }
}
