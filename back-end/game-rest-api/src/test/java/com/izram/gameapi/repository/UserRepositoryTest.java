package com.izram.gameapi.repository;

import com.izram.gameapi.model.Game;
import com.izram.gameapi.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(0, "user_test", "usertest@email", "test123", new ArrayList<Game>());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (!testInfo.getTags().contains("SkipDeleteUserById"))
            userRepository.deleteById(user.getUser_id());
        user = null;
    }

    @Test
    public void givenGetAllUsersShouldReturnListOfAllUsers(){
        User savedUser = userRepository.save(user);
        user.setUser_id(savedUser.getUser_id());
        List<User> userList = (List<User>) userRepository.findAll();
        assertThat(userList).extracting(User::getUsername).contains("user_test");
    }

    @Test
    public void givenUserToAddShouldReturnAddedUserById(){
        User savedUser = userRepository.save(user);
        user.setUser_id(savedUser.getUser_id());
        User fetchedUser = userRepository.findById(user.getUser_id()).get();
        assertEquals(user.getUsername(), fetchedUser.getUsername());
        assertEquals(user.getUser_id(), fetchedUser.getUser_id());
    }

    @Test
    public void givenUserNameShouldReturnTheRespectiveUser() {
        User savedUser = userRepository.save(user);
        user.setUser_id(savedUser.getUser_id());
        User fetchedUser = userRepository.findByUsername(user.getUsername()).get();
        assertEquals(user.getUsername(), fetchedUser.getUsername());
        assertEquals(user.getUser_id(), fetchedUser.getUser_id());
    }

    @Test
    @Tag("SkipDeleteUserById")
    public void givenUserIdTODeleteThenShouldDeleteTheUser() {
        User userToDelete = new User(0, "user_test_delete", "usertest@email", "test123", new ArrayList<Game>());
        userToDelete = userRepository.save(userToDelete);
        userRepository.deleteById(userToDelete.getUser_id());
        Optional<User> optional = userRepository.findById(userToDelete.getUser_id());
        assertEquals(Optional.empty(), optional);
    }


}