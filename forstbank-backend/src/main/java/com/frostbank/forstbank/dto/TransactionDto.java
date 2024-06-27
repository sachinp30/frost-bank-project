package com.frostbank.forstbank.dto;

import com.frostbank.forstbank.entity.Account;
import com.frostbank.forstbank.entity.Customer;
import com.frostbank.forstbank.enums.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private UUID transactionId;
    private Account account;
    private Customer customer;
    private double amount;
    private TypeOfTransaction type;
    private LocalDateTime transactionDate;
    private boolean success;
    private String errorMessage;
}
