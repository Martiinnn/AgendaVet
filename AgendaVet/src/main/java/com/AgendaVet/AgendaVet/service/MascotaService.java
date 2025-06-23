package com.AgendaVet.AgendaVet.service;

import java.util.List;
import com.AgendaVet.AgendaVet.model.Mascota;

public interface MascotaService {    
    List<Mascota> findAll();
    Mascota findById(Long id);
    Mascota save(Mascota mascota);
    void deleteById(Long id);
    Mascota patchMascota(Long id, Mascota mascota);
    List<Mascota> findByUsuarioId(Long usuarioId);
}
