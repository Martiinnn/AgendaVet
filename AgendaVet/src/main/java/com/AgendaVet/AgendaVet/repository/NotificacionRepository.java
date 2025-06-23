package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioId(Long usuarioId);
    List<Notificacion> findByUsuarioIdAndLeidoFalse(Long usuarioId);
    List<Notificacion> findByReservaId(Long reservaId);
    
    // Recibe dos parametros 
    List<Notificacion> findByUsuario_IdAndTipo(Long usuarioId, String tipo);
    
    List<Notificacion> findByTipoAndLeido(String tipo, boolean leido);
    
    List<Notificacion> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}