package com.mercadolibre.be_java_hisp_w31_g04.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromoPostDto {
    private Integer userId;
    private String userName;
    private int promoProductsCount;
}
