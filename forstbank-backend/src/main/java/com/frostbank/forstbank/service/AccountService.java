package com.frostbank.forstbank.service;

import com.frostbank.forstbank.dto.AccountDto;

import java.util.UUID;

public interface AccountService {

    AccountDto createNewAccount(AccountDto accountDto);

    AccountDto findByCustomerId(UUID customerId);
}
