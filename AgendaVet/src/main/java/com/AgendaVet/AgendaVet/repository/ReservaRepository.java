package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByVeterinariaId(Integer veterinariaId);
}