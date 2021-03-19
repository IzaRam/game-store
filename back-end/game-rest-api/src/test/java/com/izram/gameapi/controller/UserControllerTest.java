package com.izram.gameapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izram.gameapi.model.User;
import com.izram.gameapi.service.UserService;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    private User user1;
    private User user2;
    private List<User> userList;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        user1 = new User(1, "user_test_1", "user1@email.com", "test123", new ArrayList<>());
        user2 = new User(2, "user_test_2", "user2@email.com", "test123", new ArrayList<>());
        userList = new ArrayList<>(Arrays.asList(user1, user2));
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        userList = null;
    }

    @Test
    public void GetMappingOfAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(userList);
        mockMvc.perform(get("/api/v1/users/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user_test_1")))
                .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void GetMappingOfUserIdShouldReturnRespectiveUser() throws Exception {
        when(userService.findById(user1.getUser_id())).thenReturn(user1);
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user_test_1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetMappingOfUserNameShouldReturnRespectiveUser() throws Exception {
        when(userService.findByUsername(user1.getUsername())).thenReturn(user1);
        mockMvc.perform(get("/api/v1/users/name/user_test_1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user_test_1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostMappingOfUser() throws Exception {
        when(userService.addNewUser(any())).thenReturn(user1);
        mockMvc.perform(post("/api/v1/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user1)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("user_test_1")));
    }

    @Test
    public void PutMappingOfUserIdShouldReturnUpdatedUser() throws Exception {
        user2.setUser_id(user2.getUser_id());
        when(userService.editUserById(user1.getUser_id(), user2)).thenReturn(user2);
        mockMvc.perform(put("/api/v1/users/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user2)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user_test_2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PutMappingOfDeleteUserIdGameCartShouldReturnUpdatedUser() throws Exception {
        user2.setUser_id(user2.getUser_id());
        when(userService.deleteUserGameCart(user1.getUser_id(), user2)).thenReturn(user2);
        mockMvc.perform(put("/api/v1/users/edit/del/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user2)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user_test_2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteMappingOfUserIdShouldReturnDeletedUser() throws Exception {
        when(userService.deleteUserById(user1.getUser_id())).thenReturn(user1);
        mockMvc.perform(delete("/api/v1/users/del/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("user_test_1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostMappingOfVerifyUsernamePasswordShouldReturnTrue() throws Exception {
        when(userService.verifyUser(user1)).thenReturn(true);
        mockMvc.perform(post("/api/v1/users/verify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(user1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("true")))
                .andDo(MockMvcResultHandlers.print());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}