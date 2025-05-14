package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Reserva;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(Integer id);
    Reserva save(Reserva reserva);
    void deleteById(Integer id);
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByVeterinariaId(Integer veterinariaId);
    Reserva updateEstado(Integer id, String estado);
}