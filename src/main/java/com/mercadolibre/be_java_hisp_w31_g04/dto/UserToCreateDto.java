package com.mercadolibre.be_java_hisp_w31_g04.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserToCreateDto {
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 15, message = "La longitud del nombre no puede superar los 15 carácteres.")
    private String name;
}
