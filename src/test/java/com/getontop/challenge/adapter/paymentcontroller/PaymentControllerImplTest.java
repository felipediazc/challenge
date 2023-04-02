package com.getontop.challenge.adapter.paymentcontroller;

import com.getontop.challenge.domain.Payment;
import com.getontop.challenge.dto.*;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentControllerImpl.class)

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Payment payment;

    @Test
    @SneakyThrows
    void doPayment() {
        CreatePaymentResponseDto createPaymentResponseDto = new CreatePaymentResponseDto();
        RequestInfoDto requestInfoDto = new RequestInfoDto();
        requestInfoDto.setError(null);
        requestInfoDto.setStatus("OK");
        PaymentInfoDto paymentInfoDto = new PaymentInfoDto();
        paymentInfoDto.setAmount(20.0);
        paymentInfoDto.setId("A1111111");
        createPaymentResponseDto.setPaymentInfo(paymentInfoDto);
        createPaymentResponseDto.setRequestInfo(requestInfoDto);
        when(payment.doPayment(1, 1, 20.0, CurrencyEnum.USD))
                .thenReturn(createPaymentResponseDto);

        PaymentPayloadDto paymentPayloadDto = new PaymentPayloadDto();
        paymentPayloadDto.setAmount(20.0);
        paymentPayloadDto.setAccountId(1);
        paymentPayloadDto.setAccountDestinationId(1);
        paymentPayloadDto.setCurrency(CurrencyEnum.USD);

        Gson gson = new Gson();
        String jsonString = gson.toJson(paymentPayloadDto);


        mockMvc.perform(post("/api/v1/payments").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }
}