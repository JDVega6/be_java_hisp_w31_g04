package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PromoPostByUserDto {

    private int user_id;
    private String user_name;
    private List<Post> post;
}
