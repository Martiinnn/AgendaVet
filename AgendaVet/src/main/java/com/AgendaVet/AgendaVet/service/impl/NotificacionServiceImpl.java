package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Notificacion;
import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.repository.NotificacionRepository;
import com.AgendaVet.AgendaVet.service.NotificacionService;
import com.AgendaVet.AgendaVet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private ReservaService reservaService;

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
        Reserva reserva = reservaService.findById(reservaId);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }
        
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo("RESERVA");
        notificacion.setLeido(false);
        notificacion.setReserva(reserva);
        notificacion.setUsuario(reserva.getUsuario());
        
        // Crear mensaje personalizado basado en el estado de la reserva
        String mensaje = String.format("Tu reserva para %s en %s ha sido %s", 
            reserva.getMascota().getNombre(),
            reserva.getVeterinaria().getNombre(),
            reserva.getEstado().toLowerCase());
        
        notificacion.setMensaje(mensaje);
        
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