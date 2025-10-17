package com.mauricio.huespedes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HUESPEDES")
public class Huesped {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID_HUESPED")
	    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "TELEFONO", nullable = false, unique = true, length = 20)
    private String telefono;

    @Column(name = "DOCUMENTO", nullable = false, unique = true, length = 20)
    private String documento;

    @Column(name = "NACIONALIDAD", nullable = false, length = 50)
    private String nacionalidad;
}
