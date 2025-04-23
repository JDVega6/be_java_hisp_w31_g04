package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostPromoProductDto extends PostProductDto{
    @JsonProperty("has_promo")
    Boolean hasPromo;
    Double discount;

    public PostPromoProductDto(int id, int user_id, LocalDate date, ProductDto product, int category, double price,Boolean hasPromo, Double discount) {
        super(id,user_id,date,product,category,price);
        this.hasPromo = hasPromo;
        this.discount = discount;
    }
}
