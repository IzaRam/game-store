package com.izram.gameapi.controller;

import com.izram.gameapi.model.User;
import com.izram.gameapi.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody User findUserById(@PathVariable(value = "id") int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @GetMapping("/name/{usr}")
    public @ResponseBody User findUserByUsername(@PathVariable(value = "usr") String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<Object> addNewUser(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            jsonObject.put("Error", "User or Email Already Exists");
            return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.BAD_REQUEST);
        }
        jsonObject.put("Response", "User Saved");
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @PutMapping(path = "/edit/{id}")
    public @ResponseBody ResponseEntity<Object> editUserById(@PathVariable(value = "id") int id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User " + id + " Not Found"));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        updatedUser.getGameListCart().forEach(game -> {
            if (!user.getGameListCart().contains(game)) {
                user.getGameListCart().add(game);
            }
        });

        userRepository.save(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Response", "Updated " + id);
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @PutMapping(path = "/edit/del/{id}")
    public @ResponseBody ResponseEntity<Object> deleteGameCart(@PathVariable(value = "id") int id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User " + id + " Not Found"));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        updatedUser.getGameListCart().forEach(game -> {
            if (user.getGameListCart().contains(game)) {
                user.getGameListCart().remove(game);
            }
        });

        userRepository.save(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Response", "Updated " + id);
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public @ResponseBody String deleteUserById(@PathVariable(value = "id") int id) {
        userRepository.deleteById(id);
        return "Deleted";
    }

    @PostMapping("/verify")
    public @ResponseBody Boolean verifyUser(@RequestBody User user) {
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
