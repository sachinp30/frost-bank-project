package com.frostbank.forstbank.entity;

import com.frostbank.forstbank.enums.TypeOfTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TypeOfTransaction type;

    private LocalDateTime transactionDate;
    private boolean success;
    private String errorMessage;
}
