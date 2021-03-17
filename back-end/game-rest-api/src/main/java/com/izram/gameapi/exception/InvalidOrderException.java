package com.izram.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrderException extends Exception{
    public InvalidOrderException() {
        super("Game List is Empty");
    }
}
