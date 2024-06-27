package com.frostbank.forstbank.service;

import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.entity.Customer;

import java.util.UUID;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto findByCustomerId(UUID customerId);

    CustomerDto findByMobileNumber(String mobileNumber);
}
