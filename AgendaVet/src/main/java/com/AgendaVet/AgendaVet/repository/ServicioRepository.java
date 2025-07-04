package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByVeterinaria_Id(Long veterinariaId);
}