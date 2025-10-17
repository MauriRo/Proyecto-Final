package com.mauricio.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 1, max = 50)
    String nombre,

    @NotBlank(message = "El apellido es requerido")
    @Size(min = 1, max = 50)
    String apellido,

    @NotBlank(message = "El email es requerido")
    @Size(min = 5, max = 100)
    String email,

    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 5, max = 20)
    String telefono,

    @NotBlank(message = "El documento es requerido")
   
    String documento,

    @NotBlank(message = "La nacionalidad es requerida")
    @Size(min = 1, max = 50)
    String nacionalidad
) {}
