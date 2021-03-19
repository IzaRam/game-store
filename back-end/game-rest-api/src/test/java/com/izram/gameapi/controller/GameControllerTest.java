package com.izram.gameapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.service.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;
    private Game game1;
    private Game game2;
    private List<Game> gameList;

    @InjectMocks
    private GameController gameController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        game1 = new Game(1, "game_test_1", 2021, "Game for test", "www.image", 0, 0);
        game2 = new Game(2, "game_test_2", 2021, "Game for test", "www.image", 0, 0);
        gameList = new ArrayList<>(Arrays.asList(game1, game2));
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @AfterEach
    public void tearDown() {
        game1 = game2 = null;
        gameList = null;
    }

    @Test
    public void GetMappingOfAllGames() throws Exception {
        when(gameService.findAll()).thenReturn(gameList);
        mockMvc.perform(get("/api/v1/games/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("game_test_1")))
                .andDo(MockMvcResultHandlers.print());
        verify(gameService, times(1)).findAll();
    }

    @Test
    public void GetMappingOfGameIdShouldReturnRespectiveGame() throws Exception {
        when(gameService.findById(game1.getId())).thenReturn(game1);
        mockMvc.perform(get("/api/v1/games/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("game_test_1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostMappingOfGame() throws Exception {
        when(gameService.addNewGame(any())).thenReturn(game1);
        mockMvc.perform(post("/api/v1/games/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game1)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("game_test_1")));
    }

    @Test
    public void PutMappingOfGameIdShouldReturnUpdatedGame() throws Exception {
        game2.setId(game1.getId());
        when(gameService.editGameById(game1.getId(), game2)).thenReturn(game2);
        mockMvc.perform(put("/api/v1/games/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game2)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("game_test_2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteMappingOfGameIdShouldReturnDeletedGame() throws Exception {
        when(gameService.delGameById(game1.getId())).thenReturn(game1);
        mockMvc.perform(delete("/api/v1/games/del/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("game_test_1")))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}