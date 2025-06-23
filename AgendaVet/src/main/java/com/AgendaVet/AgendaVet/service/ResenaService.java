package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Resena;
import java.util.List;

public interface ResenaService {
    List<Resena> findAll();
    Resena findById(Long id);
    Resena save(Resena resena);
    void deleteById(Long id);
    List<Resena> findByVeterinariaId(Long veterinariaId);
    List<Resena> findByUsuarioId(Long usuarioId);
    Double getPromedioCalificacionByVeterinaria(Long veterinariaId);
}