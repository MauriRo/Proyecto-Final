package com.mauricio.huespedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauricio.huespedes.entity.Huesped;



public interface HuespedRepository extends JpaRepository<Huesped, Long> {

}
