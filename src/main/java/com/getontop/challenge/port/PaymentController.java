package com.getontop.challenge.port;

import com.getontop.challenge.dto.CreatePaymentResponseDto;
import com.getontop.challenge.dto.PaymentPayloadDto;
import com.getontop.challenge.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentController {
    @PostMapping("/api/v1/payments")
    @Operation(summary = "Transfers money from the ONTOP account to an user bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CreatePaymentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Payload", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    CreatePaymentResponseDto doPayment(@Valid @RequestBody PaymentPayloadDto paymentPayloadDto);
}
