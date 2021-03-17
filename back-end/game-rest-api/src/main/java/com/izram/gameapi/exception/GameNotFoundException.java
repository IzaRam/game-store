package com.izram.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends Exception{
    public GameNotFoundException(int id) {
        super("Game not found with ID " + id);
    }
}
