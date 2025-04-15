package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Post {
    private int userId;
    private int id;
    private LocalDate date;
    private Product product;
    private String category;
    private Double price;
}
