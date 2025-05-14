package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Resena;
import com.AgendaVet.AgendaVet.repository.ResenaRepository;
import com.AgendaVet.AgendaVet.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public List<Resena> findAll() {
        return resenaRepository.findAll();
    }

    @Override
    public Resena findById(Integer id) {
        return resenaRepository.findById(id).orElse(null);
    }

    @Override
    public Resena save(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Override
    public void deleteById(Integer id) {
        resenaRepository.deleteById(id);
    }

    @Override
    public List<Resena> findByVeterinariaId(Integer veterinariaId) {
        return resenaRepository.findByVeterinariaId(veterinariaId);
    }

    @Override
    public List<Resena> findByUsuarioId(Integer usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Double getPromedioCalificacionByVeterinaria(Integer veterinariaId) {
        List<Resena> resenas = findByVeterinariaId(veterinariaId);
        if (resenas.isEmpty()) {
            return 0.0;
        }
        return resenas.stream()
                .mapToInt(Resena::getPuntuacion)
                .average()
                .orElse(0.0);
    }
}