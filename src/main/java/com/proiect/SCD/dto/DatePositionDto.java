package com.proiect.SCD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class DatePositionDto {
    @NotEmpty(message = "TerminalId should not be empty")
    @NotNull(message = "TerminalId is required")
    private String terminalId;

    @NotNull(message = "Start Date is required")
    private Date startDate;

    @NotNull(message = "End Date is required")
    private Date endDate;
}
