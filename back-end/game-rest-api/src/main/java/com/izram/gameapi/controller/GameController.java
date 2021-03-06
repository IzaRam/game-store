package com.izram.gameapi.controller;

import com.izram.gameapi.model.Game;
import com.izram.gameapi.repository.GameRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @GetMapping("/games")
    public @ResponseBody Iterable<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/games/{id}")
    public @ResponseBody Game getGameById(@PathVariable(value = "id") int id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        return gameOptional.orElse(null);
    }

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<Object> addNewGame(@RequestBody Game game) {
        gameRepository.save(game);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Response", "Saved");
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @PutMapping(path = "edit/{id}")
    public @ResponseBody ResponseEntity<Object> editGamebyId(@PathVariable(value = "id") int id, @RequestBody Game updatedGame) throws NoSuchElementException {
        gameRepository.save(updatedGame);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Response", "Updated " + id);
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String delGameById(@PathVariable(value = "id") int id) {
        gameRepository.deleteById(id);
        return "Deleted";
    }

}
