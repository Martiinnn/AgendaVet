package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Resena;
import java.util.List;

public interface ResenaService {
    List<Resena> findAll();
    Resena findById(Integer id);
    Resena save(Resena resena);
    void deleteById(Integer id);
    List<Resena> findByVeterinariaId(Integer veterinariaId);
    List<Resena> findByUsuarioId(Integer usuarioId);
    Double getPromedioCalificacionByVeterinaria(Integer veterinariaId);
}