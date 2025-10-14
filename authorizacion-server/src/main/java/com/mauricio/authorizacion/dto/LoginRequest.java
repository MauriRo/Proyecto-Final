package com.mauricio.authorizacion.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
@NotBlank(message = "El username es requerido")
String username,
@NotBlank(message = "El password es requerido")
String password) {

	
}
