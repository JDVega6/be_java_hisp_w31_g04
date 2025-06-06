package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostPromoProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostByUserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostDto;

import java.util.List;

public interface IProductService {
    void createPostProduct(PostProductDto postProduct);
    void createPostProduct(PostPromoProductDto postPromoProduct);
    PromoPostDto getPromoPostCountByUserId(int userId);
    PromoPostByUserDto GetPromoPostByUser(int userId);
    List<PostProductDto> getFollowedPosts(int userId);
    FollowedPostsResponseDto getFollowedPostsResponse(int userId, String order);
}
