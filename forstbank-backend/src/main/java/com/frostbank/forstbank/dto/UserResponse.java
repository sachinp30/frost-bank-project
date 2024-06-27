package com.frostbank.forstbank.dto;

import com.frostbank.forstbank.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;
    private Integer age;
    private Gender gender;
    private String address;
}
