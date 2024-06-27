package com.frostbank.forstbank.mapper;

import com.frostbank.forstbank.dto.UserRequest;
import com.frostbank.forstbank.entity.User;

public class UserMapper {

    public static User userDtoToUser(UserRequest userRequest, String password) {

        return User.builder()
                .name(userRequest.getName())
                .gender(userRequest.getGender())
                .emailId(userRequest.getEmailId())
                .roles(userRequest.getRoles())
                .password(password)
                .build();
    }

}
