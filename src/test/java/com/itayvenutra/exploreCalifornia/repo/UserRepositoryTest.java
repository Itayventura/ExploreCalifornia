package com.itayvenutra.exploreCalifornia.repo;

import com.itayvenutra.exploreCalifornia.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testFindByUsername(){
        Optional<User> user = repository.findByUsername("admin");
        assertTrue(user.isPresent());

        user = repository.findByUsername("nobody");
        assertFalse(user.isPresent());
    }
}