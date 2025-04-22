package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Post {
    private int userId;
    private int id;
    private LocalDate date;
    private Product product;
    private int category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;
}
