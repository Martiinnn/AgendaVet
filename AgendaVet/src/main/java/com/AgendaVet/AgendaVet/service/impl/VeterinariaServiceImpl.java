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
    public Veterinaria findById(Long id) {
        return veterinariaRepository.findById(id).orElse(null);
    }

    @Override
    public Veterinaria save(Veterinaria veterinaria) {
        if (veterinaria.getId() != null) {
            Veterinaria existingVeterinaria = findById(veterinaria.getId());
            if (existingVeterinaria != null) {
                if (veterinaria.getNombre() != null) {
                    existingVeterinaria.setNombre(veterinaria.getNombre());
                }
                if (veterinaria.getDireccion() != null) {
                    existingVeterinaria.setDireccion(veterinaria.getDireccion());
                }
                if (veterinaria.getTelefono() != null) {
                    existingVeterinaria.setTelefono(veterinaria.getTelefono());
                }
                if (veterinaria.getEmail() != null) {
                    existingVeterinaria.setEmail(veterinaria.getEmail());
                }
                if (veterinaria.getHorario() != null) {
                    existingVeterinaria.setHorario(veterinaria.getHorario());
                }
                return veterinariaRepository.save(existingVeterinaria);
            }
        }
        return veterinariaRepository.save(veterinaria);
    }

    @Override
    public void deleteById(Long id) {
        veterinariaRepository.deleteById(id);
    }

    @Override
    public Servicio addServicio(Long veterinariaId, Servicio servicio) {
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
    public List<Servicio> findServiciosByVeterinariaId(Long veterinariaId) {
        Veterinaria veterinaria = findById(veterinariaId);
        return veterinaria != null ? veterinaria.getServicios() : new ArrayList<>();
    }

    @Override
    public Veterinaria updateHorarios(Long veterinariaId, String horarios) {
        Veterinaria veterinaria = findById(veterinariaId);
        if (veterinaria != null) {
            veterinaria.setHorario(horarios);
            return save(veterinaria);
        }
        return null;
    }
}