package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostPromoProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostByUserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostDto;

public interface IProductService {
    void createPostProduct(PostProductDto postProduct);
    void createPostProduct(PostPromoProductDto postPromoProduct);
    PromoPostDto getPromoPostCountByUserId(int userId);
    PromoPostByUserDto GetPromoPostByUser(int userId);
}
