package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.repository.ReservaRepository;
import com.AgendaVet.AgendaVet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteById(Integer id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public List<Reserva> findByUsuarioId(Integer usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> findByVeterinariaId(Integer veterinariaId) {
        return reservaRepository.findByVeterinariaId(veterinariaId);
    }

    @Override
    public Reserva updateEstado(Integer id, String estado) {
        Reserva reserva = findById(id);
        if (reserva != null) {
            reserva.setEstado(estado);
            return save(reserva);
        }
        return null;
    }
}