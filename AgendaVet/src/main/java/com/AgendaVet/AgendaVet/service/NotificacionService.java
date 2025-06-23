package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Notificacion;
import java.util.List;

public interface NotificacionService {
    List<Notificacion> findAll();
    Notificacion findById(Long id);
    Notificacion save(Notificacion notificacion);
    void deleteById(Long id);
    List<Notificacion> findByUsuarioId(Long usuarioId);
    Notificacion enviarNotificacionReserva(Long reservaId);
    Notificacion marcarComoLeido(Long id);
}