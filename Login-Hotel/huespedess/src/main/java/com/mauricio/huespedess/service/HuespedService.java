package com.mauricio.huespedess.service;

import com.mauricio.commons.dto.HuespedRequest;
import com.mauricio.commons.dto.HuespedResponse;

import java.util.List;

public interface HuespedService {
    HuespedResponse crearHuesped(HuespedRequest request);
    List<HuespedResponse> listarHuespedes();
    HuespedResponse obtenerHuesped(Long id);
    HuespedResponse actualizarHuesped(Long id, HuespedRequest request);
    void eliminarHuesped(Long id);
}
