package com.frostbank.forstbank.controller;

import com.frostbank.forstbank.dto.AccountDto;
import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.mapper.CustomerMapper;
import com.frostbank.forstbank.service.AccountService;
import com.frostbank.forstbank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService accountService;
    private CustomerService customerService;

    @PostMapping("/createAccount")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        if (accountDto.getCustomer() == null || accountDto.getCustomer().getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID must be provided for creating an account.");
        }

        // Set customer object from customer ID (to establish relationship)
        UUID customerId = accountDto.getCustomer().getCustomerId();
        CustomerDto customerDto = customerService.findByCustomerId(customerId);
        accountDto.setCustomer(CustomerMapper.mapToCustomer(customerDto));

        AccountDto savedAccount = accountService.createNewAccount(accountDto);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @GetMapping("/account/{customerId}")
    public ResponseEntity<AccountDto> getTransaction(@PathVariable UUID customerId) {
        AccountDto accountDto = accountService.findByCustomerId(customerId);
        return ResponseEntity.ok(accountDto);
    }
}
