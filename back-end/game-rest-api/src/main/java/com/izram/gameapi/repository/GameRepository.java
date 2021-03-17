package com.izram.gameapi.repository;

import com.izram.gameapi.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {

    Optional<Game> findByName(String name);

}
