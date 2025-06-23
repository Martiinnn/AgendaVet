package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Mascota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByPropietario_Id(Long propietarioId);
}