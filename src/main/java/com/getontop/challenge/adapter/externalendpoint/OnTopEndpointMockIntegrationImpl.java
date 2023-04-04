package com.getontop.challenge.adapter.externalendpoint;

import com.getontop.challenge.dto.*;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.exception.PaymentException500;
import com.getontop.challenge.port.ExternalEndpointIntegration;
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
public class OnTopEndpointMockIntegrationImpl implements ExternalEndpointIntegration {


    @Value("${paymentEndpoint}")
    private String paymentEndpoint;

    @Value("${balanceEndPoint}")
    private String balanceEndPoint;

    @Value("${updateWalletEndPoint}")
    private String updateWalletEndPoint;

    @Value("${webClientTimeout:15000}")
    private Integer webClientTimeout;

    @Value("${webClientRetry:1}")
    private Integer webClientRetry;

    private final Integer timeBetweenRetry = 2;

    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientTimeout)
                .responseTimeout(Duration.ofMillis(webClientTimeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(webClientTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(webClientTimeout, TimeUnit.MILLISECONDS)));
    }

    private WebClient getWebCLient(String endPointUrl) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .baseUrl(endPointUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String getPaymentErrorMessage(String message) {
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

    private String getUpdateWalletErrorMessage(String message) {
        Gson gson = new Gson();
        try {
            WalletResponseErrorDto walletResponseErrorDto = gson.fromJson(message, WalletResponseErrorDto.class);
            String code = walletResponseErrorDto.getCode();
            String errorMessage = walletResponseErrorDto.getMessage();
            return new StringBuilder("Code: ").append(code).append(". ").append(errorMessage).toString();
        } catch (Exception e1) {
            return message;
        }
    }

    @Override
    public CreatePaymentResponseDto doPayment(CreatePaymentDto createPaymentDto, final UUID localTransactionId) {
        log.info("Trying to send data to the payment endpoint. payload is: {}, localTransactionId: {}", createPaymentDto, localTransactionId);
        Mono<CreatePaymentResponseDto> createPaymentResponseDtoMono = getWebCLient(paymentEndpoint).post()
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createPaymentDto), CreatePaymentDto.class)
                .retrieve()
                .onStatus(httpStatusCode ->
                        httpStatusCode.is4xxClientError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException400(getPaymentErrorMessage(errorBody), localTransactionId)))
                )
                .onStatus(httpStatusCode ->
                        httpStatusCode.is5xxServerError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException500(getPaymentErrorMessage(errorBody), localTransactionId)))
                )
                .bodyToMono(CreatePaymentResponseDto.class)
                .retryWhen(Retry.fixedDelay(webClientRetry, Duration.ofSeconds(timeBetweenRetry))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException)
                        .doBeforeRetry(r -> log.warn("**** Retry {}", r))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new PaymentException500(retrySignal.failure().getMessage(), localTransactionId)));
        CreatePaymentResponseDto createPaymentResponseDto = createPaymentResponseDtoMono.block();
        log.info("Info has been sent to the payment endpoint. payload was: {}, response was: {}. localTransactionId: {}",
                createPaymentDto, createPaymentResponseDto, localTransactionId);
        return createPaymentResponseDto;
    }

    @Override
    public BalanceResponseDto getBalance(Integer walletId) {
        Mono<BalanceResponseDto> balanceResponseDtoMono = getWebCLient(balanceEndPoint + walletId).get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode ->
                        httpStatusCode.is4xxClientError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException400("Unexpected response")))
                )
                .onStatus(httpStatusCode ->
                        httpStatusCode.is5xxServerError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException500("Unexpected response")))
                )
                .bodyToMono(BalanceResponseDto.class)
                .retryWhen(Retry.fixedDelay(webClientRetry, Duration.ofSeconds(timeBetweenRetry))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException)
                        .doBeforeRetry(r -> log.warn("**** Retry {}", r))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new PaymentException500(retrySignal.failure().getMessage())));
        return balanceResponseDtoMono.block();
    }

    @Override
    public WalletResponseDto updateWallet(WalletPayloadDto walletPayloadDto, final UUID localTransactionId) {
        log.info("Trying to send data to the update wallet endpoint. payload is: {}, localTransactionId: {}", walletPayloadDto, localTransactionId);
        Mono<WalletResponseDto> walletResponseDtoMono = getWebCLient(updateWalletEndPoint).post()
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(walletPayloadDto), WalletPayloadDto.class)
                .retrieve()
                .onStatus(httpStatusCode ->
                        httpStatusCode.is4xxClientError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException400(getUpdateWalletErrorMessage(errorBody), localTransactionId)))
                )
                .onStatus(httpStatusCode ->
                        httpStatusCode.is5xxServerError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new PaymentException500(getUpdateWalletErrorMessage(errorBody), localTransactionId)))
                )
                .bodyToMono(WalletResponseDto.class)
                .retryWhen(Retry.fixedDelay(webClientRetry, Duration.ofSeconds(timeBetweenRetry))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException)
                        .doBeforeRetry(r -> log.warn("**** Retry {}", r))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new PaymentException500(retrySignal.failure().getMessage(), localTransactionId)));
        WalletResponseDto walletResponseDto = walletResponseDtoMono.block();
        log.info("Info has been sent to the update wallet endpoint. payload was: {}, response was: {}. localTransactionId: {}",
                walletPayloadDto, walletResponseDto, localTransactionId);
        return walletResponseDto;
    }

}
