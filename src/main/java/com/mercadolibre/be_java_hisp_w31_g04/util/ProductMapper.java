package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.ProductDto;
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

    public static Product toProduct(ProductDto product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

    public static PostProductDto toDto(Post post) {
        Product p = post.getProduct();
        ProductDto pdto = ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .type(p.getType())
                .brand(p.getBrand())
                .color(p.getColor())
                .notes(p.getNotes())
                .build();

        PostProductDto dto = new PostProductDto();
        dto.setId(post.getId());
        dto.setUser_id(post.getUserId());
        dto.setDate(post.getDate());
        dto.setProduct(pdto);
        dto.setCategory(post.getCategory());
        dto.setPrice(post.getPrice());
        return dto;
    }
}
