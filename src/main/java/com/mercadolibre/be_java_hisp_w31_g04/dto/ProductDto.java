package com.mercadolibre.be_java_hisp_w31_g04.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private int id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
