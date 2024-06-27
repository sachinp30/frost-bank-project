package com.frostbank.forstbank.mapper;

import com.frostbank.forstbank.dto.CustomerDto;
import com.frostbank.forstbank.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getMobileNumber());
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.getCustomerId(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getMobileNumber());
    }
}
