package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Reserva;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(Long id);
    Reserva save(Reserva reserva);
    void deleteById(Long id);
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByVeterinariaId(Long veterinariaId);
    Reserva updateEstado(Long id, String estado);
}