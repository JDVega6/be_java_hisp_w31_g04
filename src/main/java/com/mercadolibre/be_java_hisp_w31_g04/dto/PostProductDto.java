package com.mercadolibre.be_java_hisp_w31_g04.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostProductDto {
    private int id;
    private int user_id;
    private LocalDate date;
    private ProductDto product;
    private int category;
    private double price;
}
