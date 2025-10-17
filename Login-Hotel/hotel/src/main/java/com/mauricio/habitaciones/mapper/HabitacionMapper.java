package com.mauricio.habitaciones.mapper;

import com.mauricio.commons.dto.HabitacionRequest;
import com.mauricio.commons.dto.HabitacionResponse;
import com.mauricio.habitaciones.model.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

    public HabitacionResponse toResponse(Habitacion h) {
        HabitacionResponse r = new HabitacionResponse();
        r.setId(h.getId());
        r.setNumero(h.getNumero());
        r.setTipo(h.getTipo());
        r.setDescripcion(h.getDescripcion());
        r.setPrecio(h.getPrecio());
        r.setCapacidad(h.getCapacidad());
        r.setEstado(h.getEstado());
        return r;
    }

    public Habitacion toEntity(HabitacionRequest req) {
        Habitacion h = new Habitacion();
        h.setNumero(req.getNumero());
        h.setTipo(req.getTipo());
        h.setDescripcion(req.getDescripcion());
        h.setPrecio(req.getPrecio());
        h.setCapacidad(req.getCapacidad());
        h.setEstado(req.getEstado());
        return h;
    }

    public void updateEntity(Habitacion h, HabitacionRequest req) {
        h.setNumero(req.getNumero());
        h.setTipo(req.getTipo());
        h.setDescripcion(req.getDescripcion());
        h.setPrecio(req.getPrecio());
        h.setCapacidad(req.getCapacidad());
        h.setEstado(req.getEstado());
    }
}
