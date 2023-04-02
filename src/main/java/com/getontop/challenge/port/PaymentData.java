package com.getontop.challenge.port;


import com.getontop.challenge.adapter.paymentdata.PaymentStatus;
import com.getontop.challenge.dto.PaymentPayloadDto;

public interface PaymentData {

    void setTransaction(PaymentPayloadDto paymentPayloadDto, PaymentStatus paymentStatus);
}
