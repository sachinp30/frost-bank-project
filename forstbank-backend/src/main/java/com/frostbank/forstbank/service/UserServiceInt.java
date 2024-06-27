package com.frostbank.forstbank.service;

import com.frostbank.forstbank.dto.UserRequest;
import com.frostbank.forstbank.entity.User;

public interface UserServiceInt {
    User addUser(UserRequest userRequest);
}
