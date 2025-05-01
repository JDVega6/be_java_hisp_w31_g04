package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostPromoProductDto extends PostProductDto{
    @JsonProperty("has_promo")
    @AssertTrue(message = "No se puede crear un post de un producto en descuento sin descuento.")
    Boolean hasPromo;

    @Positive(message = "El descuento de un post de un producto tiene que ser positivo.")
    Double discount;

    public PostPromoProductDto(Integer userId, Integer id, LocalDate date, ProductDto product, Integer category, Double price,Boolean hasPromo, Double discount) {
        super(userId, id, date, product, category, price);
        this.hasPromo = hasPromo;
        this.discount = discount;
    }
}
