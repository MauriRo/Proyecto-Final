package com.hotel.reservas.service;

import com.hotel.reservas.entity.Reserva;
import java.util.List;

public interface ReservaService {
    List<Reserva> listar();
    Reserva obtenerPorId(Long id);
    Reserva crear(Reserva reserva);
    Reserva actualizar(Long id, Reserva reserva);
    void eliminar(Long id);
}
