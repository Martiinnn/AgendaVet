package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Notificacion;
import java.util.List;

public interface NotificacionService {
    List<Notificacion> findAll();
    Notificacion findById(Integer id);
    Notificacion save(Notificacion notificacion);
    void deleteById(Integer id);
    List<Notificacion> findByUsuarioId(Integer usuarioId);
    Notificacion enviarNotificacionReserva(Integer reservaId);
    Notificacion marcarComoLeido(Integer id);
}