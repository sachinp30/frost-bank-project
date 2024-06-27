package com.frostbank.forstbank.repository;

import com.frostbank.forstbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query("SELECT a FROM  Account a WHERE a.customer.customerId = :customerId")
    Account findByCustomerId(UUID customerId);
}
