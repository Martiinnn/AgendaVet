package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        return new ResponseEntity<>(reservaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Integer id) {
        Reserva reserva = reservaService.findById(id);
        return reserva != null ? 
               new ResponseEntity<>(reserva, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.save(reserva), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        Reserva existingReserva = reservaService.findById(id);
        if (existingReserva == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        reserva.setId(id);
        return new ResponseEntity<>(reservaService.save(reserva), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Integer id) {
        Reserva existingReserva = reservaService.findById(id);
        if (existingReserva == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        reservaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> getReservasByUsuario(@PathVariable Integer usuarioId) {
        return new ResponseEntity<>(reservaService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }
    
    @GetMapping("/veterinaria/{veterinariaId}")
    public ResponseEntity<List<Reserva>> getReservasByVeterinaria(@PathVariable Integer veterinariaId) {
        return new ResponseEntity<>(reservaService.findByVeterinariaId(veterinariaId), HttpStatus.OK);
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<Reserva> updateEstadoReserva(@PathVariable Integer id, @RequestBody String estado) {
        return new ResponseEntity<>(reservaService.updateEstado(id, estado), HttpStatus.OK);
    }
}
