package com.getontop.challenge.adapter.externalendpoint;

import com.getontop.challenge.dto.*;
import com.getontop.challenge.exception.PaymentException400;
import com.getontop.challenge.exception.PaymentException500;
import com.getontop.challenge.port.ExternalEndpointIntegration;
import com.getontop.challenge.util.Constants;
import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {OnTopEndpointMockIntegrationImpl.class
})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class OnTopEndpointMockIntegrationImplTest {

    @Autowired
    ExternalEndpointIntegration externalEndpointIntegration;

    static MockWebServer mockWebServer;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) throws IOException {
        r.add("balanceEndPoint", () -> "http://localhost:" + mockWebServer.getPort() + "/wallets/balance?user_id=");
        r.add("paymentEndpoint", () -> "http://localhost:" + mockWebServer.getPort() + "/api/v1/payments");
        r.add("updateWalletEndPoint", () -> "http://localhost:" + mockWebServer.getPort() + "/wallets/transactions");
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getBalance() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.BALANCE_RESPONSE)
        );
        BalanceResponseDto balanceResponseDto = externalEndpointIntegration.getBalance(1);
        assertEquals(2500.0, balanceResponseDto.getBalance());
    }

    @Test
    void getBalanceError400() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": \"xxxxxxxx\"}")
        );
        assertThrows(PaymentException400.class, () -> {
            externalEndpointIntegration.getBalance(1);
        });
    }

    @Test
    void getBalanceError500() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": \"xxxxxxxx\"}")
        );
        assertThrows(PaymentException500.class, () -> {
            externalEndpointIntegration.getBalance(1);
        });
    }

    @Test
    void doPayment() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.PAYMENT_SUCCESS_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        CreatePaymentDto createPaymentDto = gson.fromJson(Constants.PAYMENT_SUCCESS_BODY, CreatePaymentDto.class);
        CreatePaymentResponseDto createPaymentResponseDto = externalEndpointIntegration.doPayment(createPaymentDto, localTransactionId);
        assertEquals("Processing", createPaymentResponseDto.getRequestInfo().getStatus());
    }

    @Test
    void testPaymentError400() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.PAYMENT_INVALID_BODY_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        CreatePaymentDto createPaymentDto = gson.fromJson(Constants.PAYMENT_INVALID_BODY, CreatePaymentDto.class);
        assertThrows(PaymentException400.class, () -> {
            externalEndpointIntegration.doPayment(createPaymentDto, localTransactionId);
        });
    }

    @Test
    void testPaymentError500() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.PAYMENT_REJECTED_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        CreatePaymentDto createPaymentDto = gson.fromJson(Constants.PAYMENT_REJECTED_BODY, CreatePaymentDto.class);
        assertThrows(PaymentException500.class, () -> {
            externalEndpointIntegration.doPayment(createPaymentDto, localTransactionId);
        });
    }

    @Test
    void updateWallet() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.WALLET_SUCCESS_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        WalletPayloadDto walletPayloadDto = gson.fromJson(Constants.WALLET_GOOD_PAYLOAD, WalletPayloadDto.class);
        WalletResponseDto walletResponseDto = externalEndpointIntegration.updateWallet(walletPayloadDto, localTransactionId);
        assertEquals(75040, walletResponseDto.getWalletTransactionId());
        assertTrue(true);
    }

    @Test
    void testUpdateWalletError400() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.WALLET_INVALID_BODY_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        WalletPayloadDto walletPayloadDto = gson.fromJson(Constants.WALLET_INCOMPLETE_PAYLOAD, WalletPayloadDto.class);
        assertThrows(PaymentException400.class, () -> {
            externalEndpointIntegration.updateWallet(walletPayloadDto, localTransactionId);
        });
    }

    @Test
    void testUpdateWalletError500() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(Constants.WALLET_GENERIC_ERROR_RESPONSE)
        );
        UUID localTransactionId = UUID.randomUUID();
        Gson gson = new Gson();
        WalletPayloadDto walletPayloadDto = gson.fromJson(Constants.WALLET_GENERIC_ERROR_PAYLOAD, WalletPayloadDto.class);
        assertThrows(PaymentException500.class, () -> {
            externalEndpointIntegration.updateWallet(walletPayloadDto, localTransactionId);
        });
    }
}