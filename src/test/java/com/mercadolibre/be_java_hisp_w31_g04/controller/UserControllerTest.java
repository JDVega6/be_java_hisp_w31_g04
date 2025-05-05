package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUser() throws Exception{
        // Arrange
        UserDto expected= CustomFactory.getUserCreatedResponse();
        String payload=CustomFactory.getUserToCreatePayload();

        // Act and Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(expected.getUserId()))
                .andExpect(jsonPath("$.user_name").value(expected.getUserName()));



    }

    @Test
    void getUser() {
    }

    @Test
    void getUserFollowed() {
        //este
    }

    @Test
    void getUserFollowers() {
    }

    @Test
    void getUserFollowersCount() {
    }

    @Test
    void removeFollow() {
    }

    @Test
    void updateFollow() {
    }

    @Test
    void removeUser() {
    }
}