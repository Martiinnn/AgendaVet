package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Servicio;
import com.AgendaVet.AgendaVet.repository.ServicioRepository;
import com.AgendaVet.AgendaVet.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio findById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public void deleteById(Integer id) {
        servicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> findByVeterinariaId(Integer veterinariaId) {
        return servicioRepository.findByVeterinariaId(veterinariaId);
    }
}