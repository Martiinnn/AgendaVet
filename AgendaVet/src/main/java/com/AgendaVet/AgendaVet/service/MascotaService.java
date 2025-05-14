package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Mascota;
import java.util.List;

public interface MascotaService {
    List<Mascota> findAll();
    Mascota findById(Long id);
    Mascota save(Mascota mascota);
    void deleteById(Long id);
}