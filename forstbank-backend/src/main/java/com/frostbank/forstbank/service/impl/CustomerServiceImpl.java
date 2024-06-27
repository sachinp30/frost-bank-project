package com.frostbank.forstbank.service.impl;

import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.entity.Customer;
import com.frostbank.forstbank.mapper.CustomerMapper;
import com.frostbank.forstbank.repository.CustomerRepository;
import com.frostbank.forstbank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto findByCustomerId(UUID customerId) {
        return CustomerMapper.mapToCustomerDto(customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("customer not found for this customer id" + customerId)));
    }

    @Override
    public CustomerDto findByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber);
        return CustomerMapper.mapToCustomerDto(customer);
    }


}
