package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import com.AgendaVet.AgendaVet.service.VeterinariaService;
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
@RequestMapping("/api/veterinarias")
public class VeterinariaController {

    @Autowired
    private VeterinariaService veterinariaService;

    @GetMapping
    public ResponseEntity<List<Veterinaria>> getAllVeterinarias() {
        return new ResponseEntity<>(veterinariaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinaria> getVeterinariaById(@PathVariable Long id) {
        Veterinaria veterinaria = veterinariaService.findById(id);
        return veterinaria != null ? 
               new ResponseEntity<>(veterinaria, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Veterinaria> createVeterinaria(@RequestBody Veterinaria veterinaria) {
        return new ResponseEntity<>(veterinariaService.save(veterinaria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinaria> updateVeterinaria(@PathVariable Long id, @RequestBody Veterinaria veterinaria) {
        Veterinaria existingVeterinaria = veterinariaService.findById(id);
        if (existingVeterinaria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        veterinaria.setId(id);
        return new ResponseEntity<>(veterinariaService.save(veterinaria), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinaria(@PathVariable Long id) {
        Veterinaria existingVeterinaria = veterinariaService.findById(id);
        if (existingVeterinaria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        veterinariaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{veterinariaId}/servicios")
    public ResponseEntity<Servicio> addServicio(@PathVariable Long veterinariaId, @RequestBody Servicio servicio) {
        return new ResponseEntity<>(veterinariaService.addServicio(veterinariaId, servicio), HttpStatus.CREATED);
    }
    
    @GetMapping("/{veterinariaId}/servicios")
    public ResponseEntity<List<Servicio>> getServiciosByVeterinaria(@PathVariable Long veterinariaId) {
        return new ResponseEntity<>(veterinariaService.findServiciosByVeterinariaId(veterinariaId), HttpStatus.OK);
    }
    
    @PostMapping("/{veterinariaId}/horarios")
    public ResponseEntity<?> updateHorarios(@PathVariable Long veterinariaId, @RequestBody String horarios) {
        return new ResponseEntity<>(veterinariaService.updateHorarios(veterinariaId, horarios), HttpStatus.OK);
    }
}
