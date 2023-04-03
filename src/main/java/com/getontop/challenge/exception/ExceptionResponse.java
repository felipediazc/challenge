package com.getontop.challenge.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    @Schema(description = "Error date and time",
            name = "timestamp",
            example = "2023-04-03T17:59:09.241+00:00")
    private Date timestamp;
    @Schema(description = "Error message",
            name = "message",
            example = "body is invalid, check postman collection example")
    private String message;
    @Schema(description = "the URI which was called",
            name = "details",
            example = "uri=/api/v1/payments")
    private String details;
    @Schema(description = "the ID of the transaction that was made",
            name = "localTransactionId",
            example = "060659e0-0115-454f-a719-532845248c30")
    private String localTransactionId;
}
