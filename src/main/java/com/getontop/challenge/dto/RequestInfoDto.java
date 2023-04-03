package com.getontop.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestInfoDto {
    @Schema(description = "Status of the transaction",
            name = "status",
            example = "DONE")
    private String status;
    @Schema(description = "Error description (if there is no error, then this is empty)",
            name = "error",
            example = "")
    private String error;
}
