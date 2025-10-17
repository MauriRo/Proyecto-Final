package com.hotel.reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "huespedesClient", url = "http://localhost:8082/api/huespedes")
public interface HuespedClient {
    @GetMapping("/{id}")
    Map<String, Object> obtenerHuesped(@PathVariable("id") Long id);
}
