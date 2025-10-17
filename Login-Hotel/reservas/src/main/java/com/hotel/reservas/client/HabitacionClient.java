package com.hotel.reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "habitacionesClient", url = "http://localhost:8081/api/habitaciones")
public interface HabitacionClient {
    @GetMapping("/{id}")
    Map<String, Object> obtenerHabitacion(@PathVariable("id") Long id);
}
