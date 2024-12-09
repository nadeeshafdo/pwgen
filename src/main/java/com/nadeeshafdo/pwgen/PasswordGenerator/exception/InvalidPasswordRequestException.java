package com.nadeeshafdo.pwgen.PasswordGenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordRequestException extends RuntimeException {

    public InvalidPasswordRequestException(String message) {
        super(message);
    }
}