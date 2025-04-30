package com.mercadolibre.be_java_hisp_w31_g04.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
