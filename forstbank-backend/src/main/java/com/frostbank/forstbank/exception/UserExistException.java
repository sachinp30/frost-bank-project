package com.frostbank.forstbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
