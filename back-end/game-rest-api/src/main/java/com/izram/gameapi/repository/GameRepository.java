package com.izram.gameapi.repository;

import com.izram.gameapi.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {

}
