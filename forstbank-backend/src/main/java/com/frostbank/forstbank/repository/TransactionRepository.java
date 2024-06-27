package com.frostbank.forstbank.repository;

import com.frostbank.forstbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM  Transaction t inner join Account a on t.customer.customerId = a.customer.customerId WHERE a.customer.mobileNumber = :mobileNumber")
    List<Transaction> findByMobileNumber(String mobileNumber);
}
