package com.frostbank.forstbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
