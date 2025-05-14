package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import java.util.List;

public interface VeterinariaService {
    List<Veterinaria> findAll();
    Veterinaria findById(Integer id);
    Veterinaria save(Veterinaria veterinaria);
    void deleteById(Integer id);
    Servicio addServicio(Integer veterinariaId, Servicio servicio);
    List<Servicio> findServiciosByVeterinariaId(Integer veterinariaId);
    Veterinaria updateHorarios(Integer veterinariaId, String horarios);
}