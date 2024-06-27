package com.frostbank.forstbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {
    private double amount;
    private String senderMobileNumber;
    private String receiverMobileNumber;
    private LocalDateTime transferDate;
}
