package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    }

    @Test
    void getUserFollowers() {
    }

    @Test
    void getUserFollowersCount_Ok() throws Exception {
        // Arrange
        Integer id = 2;
        String expected = CustomFactory.getUserFollowersCountResponse();

        // Act & Assert
        MvcResult response = mockMvc.perform(get("/users/{userId}/followers/count", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expected, response.getResponse().getContentAsString());
    }

    @Test
    void getUserFollowersCount_NotFound() throws Exception {
        // Arrange
        Integer id = 14;
        String expected = "No se encontró ningún usuario con ese ID";

        // Act & Assert
        mockMvc.perform(get("/users/{userId}/followers/count", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));
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