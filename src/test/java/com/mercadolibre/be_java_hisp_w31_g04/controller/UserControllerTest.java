package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void getUserFollowed() throws Exception {
        // Arrange
        UserDto expected= CustomFactory.getUserFollowedResponse();
        System.out.println(expected.getFollowed());

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followed/list",2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.followed[0].user_id").value(expected.getFollowed().getFirst().getUserId()))
                .andExpect(jsonPath("$.followed[0].user_name").value(expected.getFollowed().getFirst().getUserName()))
                .andExpect(jsonPath("$.followed[1].user_id").value(expected.getFollowed().get(1).getUserId()))
                .andExpect(jsonPath("$.followed[1].user_name").value(expected.getFollowed().get(1).getUserName()));

    }
    @Test
    void getUserFollowedNotFound() throws Exception {
        // Arrange
        int id=100;
        String expected="No se encontró ningún usuario con ese ID";

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followed/list",id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));

    }

    @Test
    void getUserFollowedBadRequest() throws Exception {
        // Arrange
        int id=1;
        String expected="Parámetro 'order' inválido. Usa 'name_asc' o 'name_desc'.";

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followed/list",id)
                        .param("order","invalido"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));

    }

    @Test
    void getUserFollowers() throws Exception {
        // Arrange
        UserWithFollowersDto expected = CustomFactory.getUserFollowersResponse();
        
        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followers/list",2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.followers[0].user_id").value(expected.getFollowers().getFirst().getUserId()))
                .andExpect(jsonPath("$.followers[0].user_name").value(expected.getFollowers().getFirst().getUserName()))
                .andExpect(jsonPath("$.followers[1].user_id").value(expected.getFollowers().get(1).getUserId()))
                .andExpect(jsonPath("$.followers[1].user_name").value(expected.getFollowers().get(1).getUserName()));

    }

    @Test
    void getUserFollowersNotFound() throws Exception {
        // Arrange
        int id=100;
        String expected="No se encontró ningún usuario con ese ID";

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followers/list", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));

    }

    @Test
    void getUserFollowersBadRequest() throws Exception {
        // Arrange
        int id = 1;
        String expected="Parámetro 'order' inválido. Usa 'name_asc' o 'name_desc'.";

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/followers/list", id)
                        .param("order","invalido"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));

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
    void removeFollow_Ok() throws Exception {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 4;
        String expected = CustomFactory.getUnfollowResponse();

        // Act and Assert
        MvcResult response = mockMvc
                .perform(put("/users/{userId}/unfollow/{userIdToUnfollow}", id, idToUnfollow))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expected, response.getResponse().getContentAsString());
    }

    @Test
    void removeFollow_BadRequest_SameId() throws Exception {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = id;
        String expected = "No es posible realizar esta acción";

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/unfollow/{userIdToUnfollow}", id, idToUnfollow))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    void removeFollow_BadRequest_DoesNotFollow() throws Exception {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 2;
        String expected = "No sigues a este usuario";

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/unfollow/{userIdToUnfollow}", id, idToUnfollow))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    void removeFollow_UserNotFound_User() throws Exception {
        // Arrange
        Integer id = 100;
        Integer idToUnfollow = 4;
        String expected = "No se encontró ningún usuario con ese ID";

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/unfollow/{userIdToUnfollow}", id, idToUnfollow))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    void removeFollow_UserNotFound_UserToUnfollow() throws Exception {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 100;
        String expected = "El usuario a dejar de seguir no existe";

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/unfollow/{userIdToUnfollow}", id, idToUnfollow))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    void updateFollow() {
    }

    @Test
    void removeUser() {
    }
}