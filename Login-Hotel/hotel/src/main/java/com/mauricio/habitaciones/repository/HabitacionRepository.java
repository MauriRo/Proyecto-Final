package com.mauricio.habitaciones.repository;

import com.mauricio.habitaciones.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(Integer numero);
}
