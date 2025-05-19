package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.repository.ReservaRepository;
import com.AgendaVet.AgendaVet.repository.UsuarioRepository;
import com.AgendaVet.AgendaVet.repository.MascotaRepository;
import com.AgendaVet.AgendaVet.repository.VeterinariaRepository;
import com.AgendaVet.AgendaVet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MascotaRepository mascotaRepository;
    
    @Autowired
    private VeterinariaRepository veterinariaRepository;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public Reserva save(Reserva reserva) {
        if (reserva.getUsuarioId() != null && reserva.getUsuario() == null) {
            Usuario usuario = usuarioRepository.findById(reserva.getUsuarioId()).orElse(null);
            if (usuario != null) {
                reserva.setUsuario(usuario);
            }
        }        

        if (reserva.getMascotaId() != null && reserva.getMascota() == null) {
            Mascota mascota = mascotaRepository.findById(reserva.getMascotaId()).orElse(null);
            if (mascota != null) {
                reserva.setMascota(mascota);
            }
        }        

        if (reserva.getVeterinariaId() != null && reserva.getVeterinaria() == null) {
            Veterinaria veterinaria = veterinariaRepository.findById(reserva.getVeterinariaId()).orElse(null);
            if (veterinaria != null) {
                reserva.setVeterinaria(veterinaria);
            }
        }
        
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteById(Integer id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public List<Reserva> findByUsuarioId(Integer usuarioId) {
        return reservaRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public List<Reserva> findByVeterinariaId(Integer veterinariaId) {
        return reservaRepository.findByVeterinaria_Id(veterinariaId);
    }

    @Override
    public Reserva updateEstado(Integer id, String estado) {
        Reserva reserva = findById(id);
        if (reserva != null) {
            reserva.setEstado(estado);
            return save(reserva);
        }
        return null;
    }
}