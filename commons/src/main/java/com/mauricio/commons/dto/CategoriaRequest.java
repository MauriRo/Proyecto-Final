package com.mauricio.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(min = 1, max = 30, message = "EL nombre debe tener entre 1 y 30 caracteres")
		String nombre,
		@NotBlank(message = "La descripción es requerida")
		@Size(min = 1, max = 50, message = "La descripción debe tener entre 1 y 30 caracteres")
		String descripcion
) {}