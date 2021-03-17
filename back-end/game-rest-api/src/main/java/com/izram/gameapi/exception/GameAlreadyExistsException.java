package com.izram.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GameAlreadyExistsException extends Exception{
    public GameAlreadyExistsException(String info) {
        super("Game Already Exists: " + info);
    }
}
