package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import com.AgendaVet.AgendaVet.repository.VeterinariaRepository;
import com.AgendaVet.AgendaVet.service.VeterinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeterinariaServiceImpl implements VeterinariaService {

    @Autowired
    private VeterinariaRepository veterinariaRepository;

    @Override
    public List<Veterinaria> findAll() {
        return veterinariaRepository.findAll();
    }

    @Override
    public Veterinaria findById(Integer id) {
        return veterinariaRepository.findById(id).orElse(null);
    }

    @Override
    public Veterinaria save(Veterinaria veterinaria) {
        return veterinariaRepository.save(veterinaria);
    }

    @Override
    public void deleteById(Integer id) {
        veterinariaRepository.deleteById(id);
    }

    @Override
    public Servicio addServicio(Integer veterinariaId, Servicio servicio) {
        Veterinaria veterinaria = findById(veterinariaId);
        if (veterinaria != null) {
            servicio.setVeterinaria(veterinaria);
            if (veterinaria.getServicios() == null) {
                veterinaria.setServicios(new ArrayList<>());
            }
            veterinaria.getServicios().add(servicio);
            save(veterinaria);
            return servicio;
        }
        return null;
    }

    @Override
    public List<Servicio> findServiciosByVeterinariaId(Integer veterinariaId) {
        Veterinaria veterinaria = findById(veterinariaId);
        return veterinaria != null ? veterinaria.getServicios() : new ArrayList<>();
    }

    @Override
    public Veterinaria updateHorarios(Integer veterinariaId, String horarios) {
        Veterinaria veterinaria = findById(veterinariaId);
        if (veterinaria != null) {
            veterinaria.setHorario(horarios);
            return save(veterinaria);
        }
        return null;
    }
}