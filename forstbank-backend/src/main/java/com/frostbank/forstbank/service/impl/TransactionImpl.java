package com.frostbank.forstbank.service.impl;


import com.frostbank.forstbank.dto.AccountDto;
import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.dto.TransferRequest;
import com.frostbank.forstbank.entity.Account;
import com.frostbank.forstbank.entity.Transaction;
import com.frostbank.forstbank.enums.TypeOfTransaction;
import com.frostbank.forstbank.exception.AccountInactiveException;
import com.frostbank.forstbank.exception.InsufficientFundsException;
import com.frostbank.forstbank.exception.ResourceNotFoundException;
import com.frostbank.forstbank.mapper.AccountMapper;
import com.frostbank.forstbank.mapper.CustomerMapper;
import com.frostbank.forstbank.mapper.TransactionMapper;
import com.frostbank.forstbank.repository.AccountRepository;
import com.frostbank.forstbank.repository.TransactionRepository;
import com.frostbank.forstbank.service.AccountService;
import com.frostbank.forstbank.service.CustomerService;
import com.frostbank.forstbank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private AccountService accountService;
    private CustomerService customerService;

    @Override
    public TransactionDto processTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    public TransactionDto getTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Async
    @Transactional
    public void transferMoney(TransferRequest transferRequest, UUID transactionId) {
        CustomerDto senderCustomerDto = customerService.findByMobileNumber(transferRequest.getSenderMobileNumber());
        if (senderCustomerDto == null) {
            String errorMessage = "Sender Customer not found";
            updateTransaction(transactionId, errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        AccountDto senderAccountDto = accountService.findByCustomerId(senderCustomerDto.getCustomerId());
        if (senderAccountDto == null) {
            String errorMessage = "Sender Account not found";
            updateTransaction(transactionId, errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        if (!senderAccountDto.isActive()) {
            String errorMessage = "Sender Customer not active yet";
            updateTransaction(transactionId, errorMessage);
            throw new AccountInactiveException(errorMessage);
        }

        if(senderAccountDto.getBalance() < transferRequest.getAmount()) {
            String errorMessage = "Sender Customer not enough balance";
            updateTransaction(transactionId, errorMessage);
            throw new InsufficientFundsException(errorMessage);
        }

        CustomerDto receiverCustomerDto = customerService.findByMobileNumber(transferRequest.getReceiverMobileNumber());
        if (receiverCustomerDto == null) {
            String errorMessage = "Receiver Customer not found";
            updateTransaction(transactionId, errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        AccountDto receiverAccountDto = accountService.findByCustomerId(receiverCustomerDto.getCustomerId());
        if (receiverAccountDto == null) {
            String errorMessage = "Receiver Account not found";
            updateTransaction(transactionId, errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        if (!receiverAccountDto.isActive()) {
            String errorMessage = "Receiver Customer not active yet";
            updateTransaction(transactionId, errorMessage);
            throw new AccountInactiveException(errorMessage);
        }

        //Update sender's balance
        updateAccount(senderAccountDto.getAccountNumber(), senderAccountDto.getBalance() - transferRequest.getAmount());

        // Update receiver's balance
        updateAccount(receiverAccountDto.getAccountNumber(), receiverAccountDto.getBalance() + transferRequest.getAmount());

        // Record transaction
        //TransactionDto optionalTransaction = getTransaction(transactionId);
        Transaction senderTransaction = transactionRepository.findById(transactionId).
                orElseThrow(() -> new ResourceNotFoundException("Transaction not found for " + transactionId));
            //Transaction senderTransaction = optionalTransaction.get();
            senderTransaction.setTransactionDate(transferRequest.getTransferDate());
            senderTransaction.setCustomer(CustomerMapper.mapToCustomer(senderCustomerDto));
            senderTransaction.setAccount(AccountMapper.mapToAccount(senderAccountDto));
            senderTransaction.setType(TypeOfTransaction.WITHDRAW);
            senderTransaction.setAmount(transferRequest.getAmount());
            senderTransaction.setSuccess(true);
            senderTransaction.setTransactionId(transactionId);
            transactionRepository.save(senderTransaction);
//        Transaction senderTransaction = new Transaction();
//        senderTransaction.setTransactionDate(transferRequest.getTransferDate());
//        senderTransaction.setCustomer(CustomerMapper.mapToCustomer(senderCustomerDto));
//        senderTransaction.setAccount(AccountMapper.mapToAccount(senderAccountDto));
//        senderTransaction.setType(TypeOfTransaction.WITHDRAW);
//        senderTransaction.setAmount(transferRequest.getAmount());
//        senderTransaction.setSuccess(true);
//        senderTransaction.setTransactionId(transactionId);
//        transactionRepository.save(senderTransaction);

        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setTransactionDate(transferRequest.getTransferDate());
        receiverTransaction.setCustomer(CustomerMapper.mapToCustomer(receiverCustomerDto));
        receiverTransaction.setAccount(AccountMapper.mapToAccount(receiverAccountDto));
        receiverTransaction.setType(TypeOfTransaction.DEPOSIT);
        receiverTransaction.setAmount(transferRequest.getAmount());
        receiverTransaction.setSuccess(true);
        transactionRepository.save(receiverTransaction);
    }

    public List<TransactionDto> getAllTransactions(String mobileNumber) {
        List<Transaction> transactions = transactionRepository.findByMobileNumber(mobileNumber);
        return transactions.stream()
                .map(TransactionMapper::mapToTransactionDto)
                .collect(Collectors.toList());
    }

    @Async
    @Transactional
    public void updateAccount(UUID accountId, double amount) {
        Optional<Account> optionalTransaction = accountRepository.findById(accountId);

        if (optionalTransaction.isPresent()) {
            Account account = optionalTransaction.get();
            account.setBalance(amount);
            accountRepository.save(account);
        } else {
            // Handle transaction not found scenario, throw exception or return null/error response
            throw new ResourceNotFoundException("Account not found");
        }
    }


    @Async
    @Transactional
    public void updateTransaction(UUID transactionId, String errorMessage) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setErrorMessage(errorMessage);
            transaction.setSuccess(false);
            transactionRepository.save(transaction);
        } else {
            // Handle transaction not found scenario, throw exception or return null/error response
            throw new ResourceNotFoundException("Transaction not found");
        }
    }
}
