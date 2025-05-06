package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private static final String MESSAGE_FIELD_NOT_EMPTY = "El campo no puede estar vacío.";
    private static final String REGEX_NOT_SPECIAL_CHARACTERS = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9 _.-]+$";
    private static final String MESSAGE_NOT_SPECIAL_CHARACTER = "El campo no puede poseer caracteres especiales.";


    @JsonProperty("product_id")
    @NotNull(message = MESSAGE_FIELD_NOT_EMPTY)
    @Positive(message = "El id debe ser mayor a cero")
    private Integer id;

    @JsonProperty("product_name")
    @NotBlank(message = MESSAGE_FIELD_NOT_EMPTY)
    @Size(max = 40, message = "La longitud no puede superar los 40 caracteres.")
    @Pattern(regexp = REGEX_NOT_SPECIAL_CHARACTERS, message = MESSAGE_NOT_SPECIAL_CHARACTER)
    private String name;

    @NotBlank(message = MESSAGE_FIELD_NOT_EMPTY)
    @Size(max = 15, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = REGEX_NOT_SPECIAL_CHARACTERS, message = MESSAGE_NOT_SPECIAL_CHARACTER)
    private String type;

    @NotBlank(message = MESSAGE_FIELD_NOT_EMPTY)
    @Size(max = 25, message = "La longitud no puede superar los 25 caracteres.")
    @Pattern(regexp = REGEX_NOT_SPECIAL_CHARACTERS, message = MESSAGE_NOT_SPECIAL_CHARACTER)
    private String brand;

    @NotBlank(message = MESSAGE_FIELD_NOT_EMPTY)
    @Size(max = 15, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = REGEX_NOT_SPECIAL_CHARACTERS, message = MESSAGE_NOT_SPECIAL_CHARACTER)
    private String color;

    @Size(max = 80, message = "La longitud no puede superar los 80 caracteres.")
    @Pattern(regexp = REGEX_NOT_SPECIAL_CHARACTERS, message = MESSAGE_NOT_SPECIAL_CHARACTER)
    private String notes;
}
