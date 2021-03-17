package com.izram.gameapi.controller;

import com.izram.gameapi.exception.GameAlreadyExistsException;
import com.izram.gameapi.exception.GameNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  //includes @Controller and @ResponseBody
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameController {

    private final GameService gameService;

    @GetMapping("/all")
    public Iterable<Game> getAllGames() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable(value = "id") int id)
            throws GameNotFoundException {
        return gameService.findById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Game> addNewGame(@RequestBody Game game)
            throws GameAlreadyExistsException {
        Game savedGame = gameService.addNewGame(game);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @PutMapping(path = "edit/{id}")
    public Game editGameById(@PathVariable(value = "id") int id, @RequestBody Game updatedGame)
            throws GameNotFoundException {
        return gameService.editGameById(id, updatedGame);
    }

    @DeleteMapping("/del/{id}")
    public Game delGameById(@PathVariable(value = "id") int id)
            throws GameNotFoundException {
        return gameService.delGameById(id);
    }

}
