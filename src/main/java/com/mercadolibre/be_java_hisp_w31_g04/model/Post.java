package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Integer userId;
    private Integer id;
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;
}
