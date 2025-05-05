package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FollowedPostsResponseDto {
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("posts")
    private List<PostProductDto> posts;
}
