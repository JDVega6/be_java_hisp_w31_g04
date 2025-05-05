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
    PromoPostDto getPromoPostCountByUserId(Integer userId);
    PromoPostByUserDto getPromoPostByUser(Integer userId);
    List<PostProductDto> getFollowedPosts(Integer userId);
    FollowedPostsResponseDto getFollowedPostsFromTwoWeeks(Integer userId, String order);
}
