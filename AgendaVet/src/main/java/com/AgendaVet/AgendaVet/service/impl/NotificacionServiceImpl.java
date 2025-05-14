package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Notificacion;
import com.AgendaVet.AgendaVet.repository.NotificacionRepository;
import com.AgendaVet.AgendaVet.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion findById(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    @Override
    public Notificacion save(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void deleteById(Integer id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public List<Notificacion> findByUsuarioId(Integer usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Notificacion enviarNotificacionReserva(Integer reservaId) {
        // Hay que crear lógica para crear y enviar notificación de reserva
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo("RESERVA");
        notificacion.setLeido(false);
        // agregar la lógica para obtener la reserva y crear el mensaje del coso
        return save(notificacion);
    }

    @Override
    public Notificacion marcarComoLeido(Integer id) {
        Notificacion notificacion = findById(id);
        if (notificacion != null) {
            notificacion.setLeido(true);
            return save(notificacion);
        }
        return null;
    }
}