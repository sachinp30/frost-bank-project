package com.frostbank.forstbank.repository;

import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM  Customer c WHERE c.mobileNumber = :mobileNumber")
    Customer findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}
