package com.frostbank.forstbank.service.impl;

import com.frostbank.forstbank.dto.AccountDto;
import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.entity.Account;
import com.frostbank.forstbank.exception.ResourceNotFoundException;
import com.frostbank.forstbank.mapper.AccountMapper;
import com.frostbank.forstbank.mapper.CustomerMapper;
import com.frostbank.forstbank.repository.AccountRepository;
import com.frostbank.forstbank.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Override
    public AccountDto createNewAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto findByCustomerId(UUID customerId) {
        Account account = accountRepository.findByCustomerId(customerId);
        if (account == null) {
            throw new ResourceNotFoundException("Account with id " + customerId + " not found");
        }
        return AccountMapper.mapToAccountDto(account);
        //return AccountMapper.mapToAccountDto(accountRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Account not found for this customer id" + customerId)));
    }
}
