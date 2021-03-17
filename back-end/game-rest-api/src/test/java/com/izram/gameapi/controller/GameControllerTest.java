package com.izram.gameapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izram.gameapi.exception.GameNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.service.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;
    private Game game;
    private List<Game> gameList;

    @InjectMocks
    private GameController gameController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        game = new Game(100, "game_test", 2021, "Game for test", "www.image", 0, 0);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @AfterEach
    public void tearDown() {
        game = null;
    }


    @Test
    public void PostMappingOfGame() throws Exception {
        when(gameService.addNewGame(any())).thenReturn(game);
        mockMvc.perform(post("/api/v1/games/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(game)))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}