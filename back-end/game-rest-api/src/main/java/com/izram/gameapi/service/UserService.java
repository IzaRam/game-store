package com.izram.gameapi.service;

import com.izram.gameapi.exception.UserAlreadyExistsException;
import com.izram.gameapi.exception.UserNotFoundException;
import com.izram.gameapi.model.User;

public interface UserService {

    Iterable<User> findAll();

    User findById(int id) throws UserNotFoundException;

    User findByUsername(String username) throws UserNotFoundException;

    User addNewUser(User user) throws UserAlreadyExistsException;

    User editUserById(int id, User updatedUser) throws UserNotFoundException;

    User deleteUserGameCart(int id, User updatedUser) throws UserNotFoundException;

    User deleteUserById(int id) throws UserNotFoundException;

    Boolean verifyUser(User user);
}
