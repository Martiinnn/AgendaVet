package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.repository.MascotaRepository;
import com.AgendaVet.AgendaVet.repository.UsuarioRepository;
import com.AgendaVet.AgendaVet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }

    @Override
    public Mascota findById(Long id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    @Override
    public Mascota save(Mascota mascota) {
        // Si tiene usuarioId pero no tiene propietario completo
        if (mascota.getUsuarioId() != null && (mascota.getPropietario() == null || mascota.getPropietario().getNombre() == null)) {
            // Buscar el usuario completo en la base de datos
            Usuario usuario = usuarioRepository.findById(mascota.getUsuarioId()).orElse(null);
            if (usuario != null) {
                mascota.setPropietario(usuario);
            }
        }
        return mascotaRepository.save(mascota);
    }

    @Override
    public void deleteById(Long id) {
        mascotaRepository.deleteById(id);
    }

    @Override
    public List<Mascota> findByUsuarioId(Long usuarioId) {
        return mascotaRepository.findByPropietario_Id(usuarioId);
    }

    @Override
    public Mascota patchMascota(Long id, Mascota mascota) {
        Mascota existente = mascotaRepository.findById(id).orElse(null);
        if (existente == null) return null;
        if (mascota.getNombre() != null) existente.setNombre(mascota.getNombre());
        if (mascota.getEspecie() != null) existente.setEspecie(mascota.getEspecie());
        if (mascota.getRaza() != null) existente.setRaza(mascota.getRaza());
        if (mascota.getEdad() != null) existente.setEdad(mascota.getEdad());

        return mascotaRepository.save(existente);
    }
}