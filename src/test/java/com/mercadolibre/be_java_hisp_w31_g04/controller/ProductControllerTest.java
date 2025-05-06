package com.mercadolibre.be_java_hisp_w31_g04.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    void getPromoPostByUser() {
    }

    @Test
    void getPromoPostCount() {
    }
}