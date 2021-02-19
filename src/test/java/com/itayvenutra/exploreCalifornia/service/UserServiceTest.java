package com.itayvenutra.exploreCalifornia.service;

import com.itayvenutra.exploreCalifornia.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void signup(){
        Optional<User> user = userService.signup("dummy", "dummy", "dummy", "dummy");
        assertThat(user.get().getPassword(), not("dummy"));
        System.out.println("Encoded password = " + user.get().getPassword());
    }
}