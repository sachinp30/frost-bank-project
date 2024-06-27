package com.frostbank.forstbank.entity;


import com.frostbank.forstbank.enums.AccountType;
import jakarta.persistence.*;
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
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private boolean isActive;
    private double balance;
    private String currencyCode;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
