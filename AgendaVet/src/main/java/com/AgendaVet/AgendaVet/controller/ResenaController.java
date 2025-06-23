package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.model.Resena;
import com.AgendaVet.AgendaVet.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public ResponseEntity<List<Resena>> getAllResenas() {
        return new ResponseEntity<>(resenaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        Resena resena = resenaService.findById(id);
        return resena != null ? 
               new ResponseEntity<>(resena, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Resena> createResena(@RequestBody Resena resena) {
        return new ResponseEntity<>(resenaService.save(resena), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> updateResena(@PathVariable Long id, @RequestBody Resena resena) {
        Resena existingResena = resenaService.findById(id);
        if (existingResena == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resena.setId(id);
        return new ResponseEntity<>(resenaService.save(resena), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        Resena existingResena = resenaService.findById(id);
        if (existingResena == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resenaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/veterinaria/{veterinariaId}")
    public ResponseEntity<List<Resena>> getResenasByVeterinaria(@PathVariable Long veterinariaId) {
        return new ResponseEntity<>(resenaService.findByVeterinariaId(veterinariaId), HttpStatus.OK);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resena>> getResenasByUsuario(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(resenaService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }
    
    @GetMapping("/veterinaria/{veterinariaId}/promedio")
    public ResponseEntity<Double> getPromedioCalificacionByVeterinaria(@PathVariable Long veterinariaId) {
        return new ResponseEntity<>(resenaService.getPromedioCalificacionByVeterinaria(veterinariaId), HttpStatus.OK);
    }
}
