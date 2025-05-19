package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> findAll();
    Servicio findById(Integer id);
    Servicio save(Servicio servicio);
    void deleteById(Integer id);
    List<Servicio> findByVeterinariaId(Integer veterinariaId);
}