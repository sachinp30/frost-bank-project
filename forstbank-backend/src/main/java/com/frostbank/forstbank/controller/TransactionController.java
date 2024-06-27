package com.frostbank.forstbank.controller;

import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.dto.TransferRequest;
import com.frostbank.forstbank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping("/moneyTransfer")
    public ResponseEntity<String> processMoneyTransfer(@RequestBody TransferRequest transferRequest) {
        UUID uuid = UUID.randomUUID();
        TransactionDto transactionDto = new TransactionDto();
        //transactionDto.setTransactionId(uuid);
        transactionDto.setSuccess(false);
        TransactionDto transaction = transactionService.processTransaction(transactionDto);
        String ackMessage = "We have received your request and currently processing it. Here is your transaction id: " + transaction.getTransactionId() + " You can check the status of the transaction at /api/v1/transactions/" + transaction.getTransactionId();
        
        //async call
        transactionService.transferMoney(transferRequest, transaction.getTransactionId());
        return new ResponseEntity<>(ackMessage, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable UUID transactionId) {
        TransactionDto transactionDto = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/customer/{mobileNumber}")
    public List<TransactionDto> getCustomerTransactions(@PathVariable String mobileNumber) {
        List<TransactionDto> transactionDtoList = transactionService.getAllTransactions(mobileNumber);
        return ResponseEntity.ok().body(transactionDtoList).getBody();
    }

}
