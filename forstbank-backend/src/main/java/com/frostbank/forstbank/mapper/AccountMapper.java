package com.frostbank.forstbank.mapper;

import com.frostbank.forstbank.dto.AccountDto;
import com.frostbank.forstbank.entity.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(account.getAccountNumber(),
                account.getAccountType(),
                account.isActive(),
                account.getBalance(),
                account.getCurrencyCode(),
                account.getCustomer());
    }

    public static Account mapToAccount(AccountDto accountDto) {
        return new Account(accountDto.getAccountNumber(),
                accountDto.getAccountType(),
                accountDto.isActive(),
                accountDto.getBalance(),
                accountDto.getCurrencyCode(),
                accountDto.getCustomer());
    }
}
