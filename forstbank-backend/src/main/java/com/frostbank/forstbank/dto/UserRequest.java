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
public class UserRequest {
    private String name;
    private String emailId;
    private Gender gender;
    private String roles;
}
