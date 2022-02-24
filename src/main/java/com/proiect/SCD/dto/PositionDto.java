package com.proiect.SCD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PositionDto {

    @NotEmpty(message = "Latitude should not be empty")
    @NotNull(message = "Latitude is required")
    private String latitude;

    @NotEmpty(message = "Longitude should not be empty")
    @NotNull(message = "Longitude is required")
    private String longitude;

    @NotEmpty(message = "TerminalId should not be empty")
    @NotNull(message = "TerminalId is required")
    private String terminalId;
}
