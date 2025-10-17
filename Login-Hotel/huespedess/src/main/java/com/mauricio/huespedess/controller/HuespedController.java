package com.mauricio.huespedess.controller;

import com.mauricio.commons.dto.HuespedRequest;
import com.mauricio.commons.dto.HuespedResponse;
import com.mauricio.huespedess.service.HuespedService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/huespedes")
public class HuespedController {

    private final HuespedService service;

    public HuespedController(HuespedService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<HuespedResponse> listar() {
        return service.listarHuespedes();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public HuespedResponse obtener(@PathVariable Long id) {
        return service.obtenerHuesped(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public HuespedResponse crear(@RequestBody HuespedRequest request) {
        return service.crearHuesped(request);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public HuespedResponse actualizar(@PathVariable Long id, @RequestBody HuespedRequest request) {
        return service.actualizarHuesped(id, request);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarHuesped(id);
    }
}
