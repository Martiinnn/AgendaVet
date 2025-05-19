package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByUsuario_Id(Integer usuarioId);
    List<Resena> findByVeterinaria_Id(Integer veterinariaId);
}