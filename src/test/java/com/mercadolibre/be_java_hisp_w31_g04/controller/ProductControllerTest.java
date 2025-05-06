package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPostProduct_shouldReturn201Created() throws Exception {

    }


    @Test
    void createPostPromoProduct() {
    }

    @Test
    void getFollowedPostsFromTwoWeeks() {
    }

    @Test
    void getFollowedPostsFromTwoWeeks_shouldReturnListOfPostsSortedByDateDescending() throws Exception {

        FollowedPostsResponseDto expected = CustomFactory.getPostFollowedFromTwoWeeksResponse();

        mockMvc.perform(get("/products/followed/{userId}/list", 10)
                        .param("order", "date_desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(expected.getUserId()));
    }

    @Test
    void getFollowedPostsFromTwoWeeks_integration_emptyListWhenNoPosts() throws Exception {
        mockMvc.perform(get("/products/followed/{userId}/list", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts").isEmpty());
    }

    @Test
    void getPromoPostByUser() {
    }

    @Test
    void getPromoPostCount() {
    }
}