package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> findAll();
    Servicio findById(Long id);
    Servicio save(Servicio servicio);
    void deleteById(Long id);
    List<Servicio> findByVeterinariaId(Long veterinariaId);
}