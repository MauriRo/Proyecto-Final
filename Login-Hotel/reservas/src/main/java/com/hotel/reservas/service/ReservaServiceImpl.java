package com.hotel.reservas.service;

import com.hotel.reservas.client.HabitacionClient;
import com.hotel.reservas.client.HuespedClient;
import com.hotel.reservas.entity.Reserva;
import com.hotel.reservas.exception.ResourceNotFoundException;
import com.hotel.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository repository;
    private final HabitacionClient habitacionClient;
    private final HuespedClient huespedClient;

    public ReservaServiceImpl(ReservaRepository repository, HabitacionClient habitacionClient, HuespedClient huespedClient) {
        this.repository = repository;
        this.habitacionClient = habitacionClient;
        this.huespedClient = huespedClient;
    }

    @Override
    public List<Reserva> listar() {
        return repository.findAll();
    }

    @Override
    public Reserva obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id " + id));
    }

    @Override
    public Reserva crear(Reserva reserva) {
        if (reserva.getFechaEntrada().isAfter(reserva.getFechaSalida())) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser posterior a la de salida.");
        }

        Map<String, Object> huesped = huespedClient.obtenerHuesped(reserva.getIdHuesped());
        if (huesped == null || huesped.isEmpty()) {
            throw new IllegalArgumentException("El huésped no existe.");
        }

        Map<String, Object> habitacion = habitacionClient.obtenerHabitacion(reserva.getIdHabitacion());
        if (habitacion == null || habitacion.isEmpty()) {
            throw new IllegalArgumentException("La habitación no existe.");
        }

        double precio = Double.parseDouble(habitacion.get("precio").toString());

        long noches = ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());
        reserva.setTotal(noches * precio);
        reserva.setEstado("Confirmada");

        return repository.save(reserva);
    }

    @Override
    public Reserva actualizar(Long id, Reserva datos) {
        Reserva existente = obtenerPorId(id);
        existente.setEstado(datos.getEstado());
        existente.setFechaEntrada(datos.getFechaEntrada());
        existente.setFechaSalida(datos.getFechaSalida());
        existente.setTotal(datos.getTotal());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Reserva existente = obtenerPorId(id);
        repository.delete(existente);
    }
}
