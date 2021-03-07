package com.izram.gameapi.repository;

import com.izram.gameapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUsername(String username);

}
