package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        return new ResponseEntity<>(mascotaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        Mascota mascota = mascotaService.findById(id);
        return mascota != null ? 
               new ResponseEntity<>(mascota, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
        return new ResponseEntity<>(mascotaService.save(mascota), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
        Mascota existingMascota = mascotaService.findById(id);
        if (existingMascota == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        mascota.setId(id);
        return new ResponseEntity<>(mascotaService.save(mascota), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        Mascota existingMascota = mascotaService.findById(id);
        if (existingMascota == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        mascotaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}