package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Veterinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinariaRepository extends JpaRepository<Veterinaria, Integer> {
}