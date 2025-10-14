	package com.mauricio.authorizacion.dto;



import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		@NotBlank(message = "El username es requerido")
		@Size(min = 5, max = 20, message = "El username debe contener entre 5 y 20 caracteres")
		String username,
		@NotBlank(message = "El Password es requerido")
		@Size(min = 8, message = "El password debe contener entre 5 y 20 caracteres")
		String password,
		@NotBlank(message = "Lo roles es requerido")
		@Size(min = 1, message = "El username debe contener al menos 1 rol")
		Set<String>roles
		
		) {

}
