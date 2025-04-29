package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;

}
