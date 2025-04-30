package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostPromoProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.ProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

public class ProductMapper {

    public static Post toPost(PostProductDto postProduct, Product product) {
        return Post.builder()
                .id(postProduct.getId())
                .userId(postProduct.getUser_id())
                .date(postProduct.getDate())
                .category(postProduct.getCategory())
                .price(postProduct.getPrice())
                .product(product)
                .hasPromo(false)
                .build();

    }

    public static Post toPost(PostPromoProductDto postPromoProduct, Product product) {

        return Post.builder()
                .id(postPromoProduct.getId())
                .userId(postPromoProduct.getUser_id())
                .date(postPromoProduct.getDate())
                .category(postPromoProduct.getCategory())
                .price(postPromoProduct.getPrice())
                .product(product)
                .hasPromo(postPromoProduct.getHasPromo())
                .discount(postPromoProduct.getDiscount())
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
    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

    public static PostPromoProductDto toPostPromoDto(Post post) {
        return new PostPromoProductDto( post.getUserId(),
                                        post.getId(),
                                        post.getDate(),
                                        toProductDto(post.getProduct()),
                                        post.getCategory(),
                                        post.getPrice(),
                                        post.getHasPromo(),
                                        post.getDiscount());
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
