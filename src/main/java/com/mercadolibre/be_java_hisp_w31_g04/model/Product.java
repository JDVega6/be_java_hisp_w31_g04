package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;
    private Boolean hasPromo;
    private Double discount;
}
