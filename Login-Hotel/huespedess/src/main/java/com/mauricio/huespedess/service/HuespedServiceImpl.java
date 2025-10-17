package com.mauricio.huespedess.service;

import com.mauricio.commons.dto.HuespedRequest;
import com.mauricio.commons.dto.HuespedResponse;
import com.mauricio.huespedess.model.Huesped;
import com.mauricio.huespedess.repositories.HuespedRepository;
import com.mauricio.huespedess.exception.BadRequestException;
import com.mauricio.huespedess.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository repository;

    public HuespedServiceImpl(HuespedRepository repository) {
        this.repository = repository;
    }

    @Override
    public HuespedResponse crearHuesped(HuespedRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent())
            throw new BadRequestException("Email ya registrado");
        if (repository.findByTelefono(request.getTelefono()).isPresent())
            throw new BadRequestException("Teléfono ya registrado");

        Huesped h = new Huesped();
        h.setNombre(request.getNombre());
        h.setApellido(request.getApellido());
        h.setEmail(request.getEmail());
        h.setTelefono(request.getTelefono());
        h.setDocumento(request.getDocumento());
        h.setNacionalidad(request.getNacionalidad());

        repository.save(h);
        return mapToResponse(h);
    }

    @Override
    public List<HuespedResponse> listarHuespedes() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public HuespedResponse obtenerHuesped(Long id) {
        Huesped h = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Huésped no encontrado"));
        return mapToResponse(h);
    }

    @Override
    public HuespedResponse actualizarHuesped(Long id, HuespedRequest request) {
        Huesped h = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Huésped no encontrado"));

        h.setNombre(request.getNombre());
        h.setApellido(request.getApellido());
        h.setEmail(request.getEmail());
        h.setTelefono(request.getTelefono());
        h.setDocumento(request.getDocumento());
        h.setNacionalidad(request.getNacionalidad());

        repository.save(h);
        return mapToResponse(h);
    }

    @Override
    public void eliminarHuesped(Long id) {
        Huesped h = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Huésped no encontrado"));
        repository.delete(h);
    }

    private HuespedResponse mapToResponse(Huesped h) {
        return new HuespedResponse(
                h.getId(),
                h.getNombre(),
                h.getApellido(),
                h.getEmail(),
                h.getTelefono(),
                h.getDocumento(),
                h.getNacionalidad()
        );
    }
}
