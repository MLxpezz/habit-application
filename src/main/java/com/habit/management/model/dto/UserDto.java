package com.habit.management.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDto(
        @NotBlank(message = "El campo es requerido")
        @Email(message = "El email no es valido")
        String email,
        @NotBlank(message = "El campo es requerido")
        @Size(min = 8, message = "El campo debe contener un minimo de 8 caracteres")
        String password
) {
}
