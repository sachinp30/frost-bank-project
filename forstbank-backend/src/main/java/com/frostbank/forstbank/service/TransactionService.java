package com.frostbank.forstbank.service;

import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.dto.TransferRequest;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDto processTransaction(TransactionDto transactionDto);

    TransactionDto getTransaction(UUID id);

    void transferMoney(TransferRequest request, UUID id);

    List<TransactionDto> getAllTransactions(String mobileNumber);
}
