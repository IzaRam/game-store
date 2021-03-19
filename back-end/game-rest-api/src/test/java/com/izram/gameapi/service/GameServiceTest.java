package com.izram.gameapi.service;

import com.izram.gameapi.exception.GameAlreadyExistsException;
import com.izram.gameapi.exception.GameNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Autowired
    @InjectMocks
    private GameServiceImpl gameService;
    private Game game1;
    private Game game2;
    private List<Game> gameList;

    @BeforeEach
    public void setUp() {
        game1 = new Game(1, "game_test_1", 2021, "Game for test", "www.image_url", 0, 0);
        game2 = new Game(2, "game_test_2", 2021, "Game for test", "www.image_url", 0, 0);
        gameList = new ArrayList<>(Arrays.asList(game1, game2));
    }

    @AfterEach
    public void tearDown() {
        game1 = game2 = null;
        gameList = null;
    }

    @Test
    public void givenGetAllGamesShouldReturnListOfAllGames(){
        when(gameRepository.findAll()).thenReturn(gameList);
        List<Game> gameListReturned = (List<Game>) gameService.findAll();
        assertEquals(gameListReturned, gameList);
        verify(gameRepository,times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnGameOfThatId() throws GameNotFoundException {
        when(gameRepository.findById(game1.getId())).thenReturn(Optional.ofNullable(game1));
        assertThat(gameService.findById(game1.getId())).isEqualTo(game1);
        verify(gameRepository, times(1)).findById(any());
    }

    @Test
    void givenGameToAddShouldReturnAddedGame() throws GameAlreadyExistsException {
        when(gameRepository.save(any())).thenReturn(game1);
        Game savedGame = gameService.addNewGame(game1);
        assertEquals(savedGame, game1);
        verify(gameRepository,times(1)).save(any());
    }

    @Test
    void givenSameGameToAddTwoTimesShouldReturnError() throws GameAlreadyExistsException {
        when(gameRepository.save(any())).thenReturn(game1);
        gameService.addNewGame(game1);
        when(gameRepository.findByName(any())).thenReturn(Optional.ofNullable(game1));
        GameAlreadyExistsException e = assertThrows(
                GameAlreadyExistsException.class,
                () -> gameService.addNewGame(game1));
        assertTrue(e.getMessage().contains("Game Already Exists: game_test_1"));
        verify(gameRepository,times(1)).save(any());
        verify(gameRepository,times(2)).findByName(any());
    }

    @Test
    public void givenIdToEditShouldEditTheGameOfThatId() throws GameNotFoundException {
        when(gameRepository.findById(game1.getId())).thenReturn(Optional.ofNullable(game1));
        game2.setId(game1.getId());
        when(gameRepository.save(any())).thenReturn(game2);
        Game editedGame = gameService.editGameById(game1.getId(), game2);
        assertEquals(game2, editedGame);
        verify(gameRepository, times(1)).findById(any());
        verify(gameRepository, times(1)).save(any());
    }

    @Test
    public void givenIdToDeleteThenShouldDeleteTheGameOfThatId() throws GameNotFoundException {
        when(gameRepository.findById(game1.getId())).thenReturn(Optional.ofNullable(game1));
        Game deletedGame = gameService.delGameById(game1.getId());
        assertEquals(deletedGame, game1);
        verify(gameRepository,times(1)).findById(any());
        verify(gameRepository,times(1)).deleteById(any());
    }

    @Test
    public void givenIdToDeleteThenShouldReturnErrorIfGameNotFound() {
        when(gameRepository.findById(game1.getId())).thenReturn(Optional.empty());
        GameNotFoundException e = assertThrows(GameNotFoundException.class,
                () -> gameService.delGameById(game1.getId()));
        assertTrue(e.getMessage().contains("Game not found with ID " + game1.getId()));
        verify(gameRepository,times(1)).findById(any());
        verify(gameRepository,times(0)).deleteById(any());
    }


}