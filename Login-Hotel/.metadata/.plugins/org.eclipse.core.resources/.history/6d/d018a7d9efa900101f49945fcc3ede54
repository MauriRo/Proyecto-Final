package com.christian.oauth.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		@NotBlank(message = "El username es requerido")
		@Size(min = 5, max = 20, message = "El username debe contener entre 5 y 20 caracteres")
		String username,
		@NotBlank(message = "La password es requerida")
		@Size(min = 8, message = "La password debe contener minimo 8 caracteres")
		String password,
		@NotNull(message = "Los roles son requeridos")
		@Size(min = 1, message = "El usuario debe tener al menos 1 rol")
		Set<String> roles
) {}