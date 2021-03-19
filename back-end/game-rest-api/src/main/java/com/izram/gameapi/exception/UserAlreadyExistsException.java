package com.izram.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String info) {
        super("User Already Exists " + info);
    }
}
