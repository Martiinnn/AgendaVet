package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // No tengo nada para poner aqui XD, aún
}