package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import java.util.List;

public interface VeterinariaService {
    List<Veterinaria> findAll();
    Veterinaria findById(Long id);
    Veterinaria save(Veterinaria veterinaria);
    void deleteById(Long id);
    Servicio addServicio(Long veterinariaId, Servicio servicio);
    List<Servicio> findServiciosByVeterinariaId(Long veterinariaId);
    Veterinaria updateHorarios(Long veterinariaId, String horarios);
}