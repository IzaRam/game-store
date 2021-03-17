package com.izram.gameapi.service;

import com.izram.gameapi.exception.GameAlreadyExistsException;
import com.izram.gameapi.exception.GameNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameServiceImpl implements  GameService {

    private final GameRepository gameRepository;

    public Iterable<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(int id) throws GameNotFoundException {
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    public Game addNewGame(Game game) throws GameAlreadyExistsException {
        Optional<Game> optionalGame = gameRepository.findByName(game.getName());
        if (optionalGame.isPresent()) {
            throw new GameAlreadyExistsException(game.getName());
        }
        return gameRepository.save(game);
    }

    public Game editGameById(int id, Game updatedGame)
            throws GameNotFoundException {
        gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        return gameRepository.save(updatedGame);
    }

    public Game delGameById(int id) throws GameNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(() ->
                new GameNotFoundException(id));
        gameRepository.deleteById(id);
        return game;
    }
}
