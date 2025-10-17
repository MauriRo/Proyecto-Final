package com.hotel.reservas.repository;

import com.hotel.reservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.idHabitacion = :idHabitacion " +
           "AND r.estado IN ('Confirmada', 'En curso') " +
           "AND (r.fechaEntrada <= :fechaSalida AND r.fechaSalida >= :fechaEntrada)")
    List<Reserva> findReservasSuperpuestas(Long idHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida);
}
