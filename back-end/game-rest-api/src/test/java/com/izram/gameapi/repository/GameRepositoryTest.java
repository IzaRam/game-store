package com.izram.gameapi.repository;

import com.izram.gameapi.model.Game;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(0, "game_test", 2021, "Game for test", "www.image_url", 0, 0);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (!testInfo.getTags().contains("SkipDeleteGameById"))
            gameRepository.deleteById(game.getId());
        game = null;
    }

    @Test
    public void givenGameToAddShouldReturnAddedGame(){
        Game savedGame = gameRepository.save(game);
        game.setId(savedGame.getId());
        Game fetchedGame = gameRepository.findById(game.getId()).get();
        assertEquals(game.getName(), fetchedGame.getName());
        assertEquals(game.getId(), fetchedGame.getId());
    }

    @Test
    public void givenGetAllGamesShouldReturnListOfAllGames(){
        Game savedGame = gameRepository.save(game);
        game.setId(savedGame.getId());
        List<Game> gameList = (List<Game>) gameRepository.findAll();
        assertThat(gameList).extracting(Game::getName).contains("game_test");
    }

    @Test
    @Tag("SkipDeleteGameById")
    public void givenIdTODeleteThenShouldDeleteTheGame() {
        Game gameToDelete = new Game(0,"game_test_to_delete", 2021, "Game for test delete", "www.image_url", 0, 0);
        gameToDelete = gameRepository.save(gameToDelete);
        gameRepository.deleteById(gameToDelete.getId());
        Optional<Game> optional = gameRepository.findById(gameToDelete.getId());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    public void givenGameNameShouldReturnTheRespectiveGame() {
        Game savedGame = gameRepository.save(game);
        game.setId(savedGame.getId());
        Game fetchedGame = gameRepository.findByName(game.getName()).get();
        assertEquals(game.getName(), fetchedGame.getName());
        assertEquals(game.getId(), fetchedGame.getId());
    }

}