package com.getontop.challenge.util;

public class PaymentConstants {

    public static final String ERROR_INVALID_BODY = "body is invalid, check postman collection example";
    public static final String ERROR_INVALID_ACCOUNT_ID = "invalid account id";
    public static final String ERROR_INVALID_AMOUNT = "Invalid amount";
    public static final String ERROR_NO_SUFFICIENT_FUNDS = "No sufficient funds";
    public static final String ERROR_INVALID_ACCOUNT_DESTINATION_ID = "invalid wallet id";
    public static final String ENDPOINT_PROCESSING_STATUS_STRING = "Processing";
    public static final String BANK_TRANSFER_TEXT_DESCRIPTION = "Bank transfer to your account";

    public static Double getTransactionFee(Double amount) {
        return (amount * 0.1);
    }

}
