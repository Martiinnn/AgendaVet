package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByUsuario_Id(Long usuarioId);
    List<Resena> findByVeterinaria_Id(Long veterinariaId);    

    List<Resena> findByUsuario_IdAndPuntuacion(Long usuarioId, Integer puntuacion);
    
    List<Resena> findByVeterinaria_IdAndPuntuacionGreaterThanEqual(Long veterinariaId, Integer puntuacion);
    
    List<Resena> findByPuntuacionBetween(Integer puntuacionMin, Integer puntuacionMax);
}