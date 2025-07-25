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
    public Resena findById(Long id) {
        return resenaRepository.findById(id).orElse(null);
    }

    @Override
    public Resena save(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Override
    public void deleteById(Long id) {
        resenaRepository.deleteById(id);
    }

    @Override
    public List<Resena> findByVeterinariaId(Long veterinariaId) {
        return resenaRepository.findByVeterinaria_Id(veterinariaId);
    }

    @Override
    public List<Resena> findByUsuarioId(Long usuarioId) {
        return resenaRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public Double getPromedioCalificacionByVeterinaria(Long veterinariaId) {
        List<Resena> resenas = findByVeterinariaId(veterinariaId);
        if (resenas.isEmpty()) {
            return 0.0;
        }
        return resenas.stream()
                .mapToDouble(Resena::getPuntuacion)
                .average()
                .orElse(0.0);
    }
}