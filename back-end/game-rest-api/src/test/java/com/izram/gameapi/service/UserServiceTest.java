package com.izram.gameapi.service;

import com.izram.gameapi.exception.UserAlreadyExistsException;
import com.izram.gameapi.exception.UserNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.model.User;
import com.izram.gameapi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;
    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    public void setUp() {
        user1 = new User(1, "user_test_1", "usertest1@email", "test123", new ArrayList<>());
        user2 = new User(2, "user_test_2", "usertest2@email", "test123", new ArrayList<>());
        userList = new ArrayList<>(Arrays.asList(user1, user2));
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        userList = null;
    }

    @Test
    public void givenGetAllUsersShouldReturnListOfAllUsers(){
        when(userRepository.findAll()).thenReturn(userList);
        List<User> userListReturned = (List<User>) userService.findAll();
        assertEquals(userListReturned, userList);
        verify(userRepository,times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnUserOfThatId() throws UserNotFoundException {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        assertThat(userService.findById(user1.getUser_id())).isEqualTo(user1);
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void givenUserIdThenShouldReturnUserNotFoundException() {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.empty());
        UserNotFoundException e = assertThrows(UserNotFoundException.class,
                () -> userService.findById(user1.getUser_id()));
        assertTrue(e.getMessage().contains("User Not Found: " + user1.getUser_id()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void givenUserNameThenShouldReturnUserOfThatUsername() throws UserNotFoundException {
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.ofNullable(user1));
        assertThat(userService.findByUsername(user1.getUsername())).isEqualTo(user1);
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    public void givenUserNameThenShouldReturnUserNotFoundException() {
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());
        UserNotFoundException e = assertThrows(UserNotFoundException.class,
                () -> userService.findByUsername(user1.getUsername()));
        assertTrue(e.getMessage().contains("User Not Found: " + user1.getUsername()));
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void givenUserToAddShouldReturnAddedUser() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenReturn(user1);
        User savedUser = userService.addNewUser(user1);
        assertEquals(user1, savedUser);
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void givenSameUserToAddTwoTimesShouldReturnError() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenReturn(user1);
        userService.addNewUser(user1);
        when(userRepository.save(user1)).thenThrow(new DataIntegrityViolationException("Username already exists"));
        UserAlreadyExistsException e = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.addNewUser(user1));
        assertTrue(e.getMessage().contains("User Already Exists " + user1.getUsername()));
        verify(userRepository,times(2)).save(any());
    }

    @Test
    public void givenUserIdToEditShouldEditTheUserOfThatId() throws UserNotFoundException {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        Game game = new Game(1, "game_test", 2021, "game for test", "www.image_url", 0, 0);
        user2.setGameListCart(new ArrayList<>(Arrays.asList(game)));
        user2.setUser_id(user1.getUser_id());
        userService.editUserById(user1.getUser_id(), user2);
        assertEquals(user2, user1);
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void givenUserIdToEditShouldThrowUserNotFoundException() {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.empty());
        user2.setUser_id(user1.getUser_id());
        UserNotFoundException e = assertThrows(UserNotFoundException.class,
                () -> userService.editUserById(user1.getUser_id(), user2));
        assertTrue(e.getMessage().contains("User Not Found: " + user1.getUser_id()));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void givenUserIdToDeleteThenShouldDeleteTheUserOfThatId() throws UserNotFoundException {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        User deletedUser = userService.deleteUserById(user1.getUser_id());
        assertEquals(deletedUser, user1);
        verify(userRepository,times(1)).findById(any());
        verify(userRepository,times(1)).deleteById(any());
    }

    @Test
    public void givenUserToVerifyThenShouldReturnTrueIfUserExists() {
        when(userRepository.findAll()).thenReturn(userList);
        Boolean result = userService.verifyUser(user1);
        assertEquals(true, result);
        User newUser = new User(3, "user_test_that_not_exists", "usertest3@email", "test123", new ArrayList<>());
        result = userService.verifyUser(newUser);
        assertEquals(false, result);
    }

    @Test
    public void givenUserThenShouldDeleteGamesInCart() throws UserNotFoundException {
        Game game = new Game(1, "game_test", 2021, "game for test", "www.image_url", 0, 0);
        user1.setGameListCart(new ArrayList<>(Arrays.asList(game)));
        user2.setGameListCart(new ArrayList<>(Arrays.asList(game)));
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        userService.deleteUserGameCart(user1.getUser_id(), user2);
        assertTrue(user1.getGameListCart().isEmpty());
    }



}