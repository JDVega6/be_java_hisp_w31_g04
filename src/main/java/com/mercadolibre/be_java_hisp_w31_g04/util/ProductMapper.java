package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

public class ProductMapper {
    public static Post toPost(PostProductDto postProduct, Product product) {

        return Post.builder()
                .userId(postProduct.getUser_id())
                .date(postProduct.getDate())
                .category(postProduct.getCategory())
                .price(postProduct.getPrice())
                .product(product)
                .build();

    }
}
