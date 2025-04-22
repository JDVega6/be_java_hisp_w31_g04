package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PromoPostByUserDto {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    private List<Post> post;
}
