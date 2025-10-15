package com.mauricio.commons.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(min = 1, max = 30, message = "El nombre debe tener entre 1 y 30 caracteres")
		String nombre,
		@NotBlank(message = "La descripción es requerida")
		@Size(min = 1, max = 50, message = "La descripción debe tener entre 1 y 50 caracteres")
		String descripcion,
		@NotNull(message = "El precio es requerido")
		@Positive(message = "El precio debe ser positivo")
		Double precio,
		@NotNull(message = "El stock es requerido")
		@Positive(message = "El stock debe ser positivo")
		Integer stock,
		@NotNull(message = "El id de la categoría es requerido")
		@Positive(message = "El id de la categoría debe ser un entero positivo")
		Long idCategoria,
		@NotNull(message = "El id de proveedores es requerido")
		@Size(min = 1, message = "Debe haber por lo menos 1 id de proveedor")
		Set<Long> idProveedores
) {}