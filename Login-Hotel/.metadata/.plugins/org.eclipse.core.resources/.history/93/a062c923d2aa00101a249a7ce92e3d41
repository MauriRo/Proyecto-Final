package com.mauricio.huespedes.mapper;

import org.springframework.stereotype.Component;
import com.mauricio.huespedes.entity.Huesped;
import com.mauricio.commons.dto.HuespedRequest;
import com.mauricio.commons.dto.HuespedResponse;
import com.mauricio.commons.mappers.CommonMapper;

@Component
public class HuespedMapper extends CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

    public HuespedResponse entityToResponse(Huesped entity) {
        if (entity == null) return null;
        return new HuespedResponse(
            entity.getId(),
            entity.getNombre(),
            entity.getApellido(),
            entity.getEmail(),
            entity.getTelefono(),
            entity.getDocumento(),
            entity.getNacionalidad()
        );
    }

    public Huesped requestToEntity(HuespedRequest request) {
        if (request == null) return null;
        Huesped huesped = new Huesped();
        huesped.setNombre(request.nombre());
        huesped.setApellido(request.apellido());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setDocumento(request.documento());
        huesped.setNacionalidad(request.nacionalidad());
        return huesped;
    }
}
