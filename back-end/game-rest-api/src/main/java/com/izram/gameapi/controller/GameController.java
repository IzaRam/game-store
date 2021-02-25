package com.izram.gameapi.controller;

import com.izram.gameapi.model.Game;
import com.izram.gameapi.repository.GameRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET}, allowedHeaders = "*")
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
    public @ResponseBody JSONObject addNewGame(@RequestBody Game game) {
        gameRepository.save(game);
        return new JSONObject().put("Response", "Saved");
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String delGameById(@PathVariable(value = "id") int id) {
        gameRepository.deleteById(id);
        return "Deleted";
    }

}
