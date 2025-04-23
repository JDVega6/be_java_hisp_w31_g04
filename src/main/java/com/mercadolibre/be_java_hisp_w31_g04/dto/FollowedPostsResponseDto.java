package com.mercadolibre.be_java_hisp_w31_g04.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowedPostsResponseDto {
    private int userId;
    private List<PostProductDto> posts;
}
