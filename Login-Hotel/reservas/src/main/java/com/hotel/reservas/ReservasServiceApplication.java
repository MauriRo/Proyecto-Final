package com.hotel.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservasServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservasServiceApplication.class, args);
    }
}
