package com.izram.gameapi.service;

import com.izram.gameapi.exception.GameAlreadyExistsException;
import com.izram.gameapi.exception.GameNotFoundException;
import com.izram.gameapi.model.Game;

public interface GameService {
    Iterable<Game> findAll();

    Game findById(int id) throws GameNotFoundException;

    Game addNewGame(Game game) throws GameAlreadyExistsException;

    Game editGameById(int id, Game updatedGame) throws GameNotFoundException;

    Game delGameById(int id) throws GameNotFoundException;
}
