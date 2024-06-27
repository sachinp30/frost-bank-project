package com.frostbank.forstbank.mapper;

import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getTransactionId(),
                transaction.getAccount(),
                transaction.getCustomer(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTransactionDate(),
                transaction.isSuccess(),
                transaction.getErrorMessage());
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        return new Transaction(transactionDto.getTransactionId(),
                transactionDto.getAccount(),
                transactionDto.getCustomer(),
                transactionDto.getAmount(),
                transactionDto.getType(),
                transactionDto.getTransactionDate(),
                transactionDto.isSuccess(),
                transactionDto.getErrorMessage());
    }
}
