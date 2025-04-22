package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;

import java.util.List;

public interface IProductService {
    void createPostProduct(PostProductDto PostProduct);
    List<PostProductDto> getFollowedPosts(int userId);
}
