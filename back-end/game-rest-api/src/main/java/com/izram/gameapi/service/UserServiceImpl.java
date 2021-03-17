package com.izram.gameapi.service;

import com.izram.gameapi.exception.UserAlreadyExistsException;
import com.izram.gameapi.exception.UserNotFoundException;
import com.izram.gameapi.model.User;
import com.izram.gameapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.valueOf(id)));
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException(username));
    }

    @Override
    public User addNewUser(User user)
            throws UserAlreadyExistsException {
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return savedUser;
    }

    @Override
    public User editUserById(int id, User updatedUser) throws UserNotFoundException {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(updatedUser.getUsername()));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        updatedUser.getGameListCart().forEach(game -> {
            if (!user.getGameListCart().contains(game)) {
                user.getGameListCart().add(game);
            }
        });

        return userRepository.save(user);
    }

    @Override
    public User deleteUserGameCart(int id, User updatedUser) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.valueOf(id)));

        updatedUser.getGameListCart().forEach(game ->
            user.getGameListCart().remove(game));
        return userRepository.save(user);
    }

    @Override
    public User deleteUserById(int id) throws UserNotFoundException {
        User deletedUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.valueOf(id)));
        userRepository.deleteById(id);
        return deletedUser;
    }

    @Override
    public Boolean verifyUser(User user) {
        Iterable<User> userList = userRepository.findAll();
        for (User u : userList) {
            if (u.getUsername().equals(user.getUsername())
                    && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
