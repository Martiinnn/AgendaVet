package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Reserva;
import java.util.List;
import java.time.LocalDate;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(Long id);
    Reserva save(Reserva reserva);
    void deleteById(Long id);
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByVeterinariaId(Long veterinariaId);
    Reserva updateEstado(Long id, String estado);
    
    // estos son los con 2 parametros
    List<Reserva> findByUsuarioIdAndEstado(Long usuarioId, String estado);
    List<Reserva> findByVeterinariaIdAndFecha(Long veterinariaId, LocalDate fecha);
    List<Reserva> findByUsuarioIdAndVeterinariaId(Long usuarioId, Long veterinariaId);
    List<Reserva> findByMascotaIdAndEstado(Long mascotaId, String estado);
}