package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.model.Notificacion;
import com.AgendaVet.AgendaVet.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        return new ResponseEntity<>(notificacionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getNotificacionById(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.findById(id);
        return notificacion != null ? 
               new ResponseEntity<>(notificacion, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Notificacion> createNotificacion(@RequestBody Notificacion notificacion) {
        return new ResponseEntity<>(notificacionService.save(notificacion), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> updateNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacion) {
        Notificacion existingNotificacion = notificacionService.findById(id);
        if (existingNotificacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        notificacion.setId(id);
        return new ResponseEntity<>(notificacionService.save(notificacion), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        Notificacion existingNotificacion = notificacionService.findById(id);
        if (existingNotificacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        notificacionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> getNotificacionesByUsuario(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(notificacionService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }
    
    @PostMapping("/enviar/reserva/{reservaId}")
    public ResponseEntity<Notificacion> enviarNotificacionReserva(@PathVariable Long reservaId) {
        return new ResponseEntity<>(notificacionService.enviarNotificacionReserva(reservaId), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}/marcar-leido")
    public ResponseEntity<Notificacion> marcarComoLeido(@PathVariable Long id) {
        return new ResponseEntity<>(notificacionService.marcarComoLeido(id), HttpStatus.OK);
    }
}
