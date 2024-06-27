package com.frostbank.forstbank.dto;

import com.frostbank.forstbank.entity.Customer;
import com.frostbank.forstbank.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private UUID accountNumber;
    private AccountType accountType;
    private boolean isActive;
    private double balance;
    private String currencyCode;
    private Customer customer;
}
