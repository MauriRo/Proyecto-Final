package com.mauricio.habitaciones.service;

import com.mauricio.commons.dto.HabitacionRequest;
import com.mauricio.commons.dto.HabitacionResponse;

import java.util.List;

public interface HabitacionService {
    List<HabitacionResponse> getAll();
    HabitacionResponse create(HabitacionRequest request);
    HabitacionResponse update(Long id, HabitacionRequest request);
    void delete(Long id);
}
