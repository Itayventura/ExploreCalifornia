package com.itayvenutra.exploreCalifornia.web;

import com.itayvenutra.exploreCalifornia.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Test
    public void login(){

    }

    @Test
    public void getAllUsers(){
        
    }
}