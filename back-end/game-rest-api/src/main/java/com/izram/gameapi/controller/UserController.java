package com.izram.gameapi.controller;

import com.izram.gameapi.exception.UserAlreadyExistsException;
import com.izram.gameapi.exception.UserNotFoundException;
import com.izram.gameapi.model.User;
import com.izram.gameapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable(value = "id") int id)
            throws UserNotFoundException {
        return userService.findById(id);
    }

    @GetMapping("/name/{usr}")
    public User findUserByUsername(@PathVariable(value = "usr") String username)
            throws UserNotFoundException {
        return userService.findByUsername(username);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user)
            throws UserAlreadyExistsException {
        User savedUser = userService.addNewUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "/edit/{id}")
    public User editUserById(@PathVariable(value = "id") int id, @RequestBody User updatedUser)
            throws UserNotFoundException {
        return userService.editUserById(id, updatedUser);
    }

    @PutMapping(path = "/edit/del/{id}")
    public User deleteUserGameCartById(@PathVariable(value = "id") int id, @RequestBody User updatedUser)
            throws UserNotFoundException {
        return userService.deleteUserGameCart(id, updatedUser);
    }

    @DeleteMapping("/del/{id}")
    public User deleteUserById(@PathVariable(value = "id") int id) throws UserNotFoundException {
        return userService.deleteUserById(id);
    }

    @PostMapping("/verify")
    public Boolean verifyUser(@RequestBody User user) {
        return userService.verifyUser(user);
    }

}
