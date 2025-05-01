package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProductDto {

    @JsonProperty("user_id")
    @NotBlank(message = "El  id no puede estar vacío.")
    @Positive(message = "El id debe ser mayor a cero.")
    private Integer userId;

    private Integer id;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
   @NotBlank(message = "La fecha no puede estar vacía.")
    private LocalDate date;

   @Valid
    private ProductDto product;

   @NotBlank(message = "El campo no puede estar vacío.")
    private Integer category;

   @NotBlank(message = "El campo no puede estar vacío.")
   @DecimalMax(value = "10000000", inclusive = true, message = "El precio máximo por producto es de 10.000.000")
    private Double price;
}
