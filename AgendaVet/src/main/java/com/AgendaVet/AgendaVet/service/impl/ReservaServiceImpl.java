package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import com.AgendaVet.AgendaVet.repository.ReservaRepository;
import com.AgendaVet.AgendaVet.service.ReservaService;
import com.AgendaVet.AgendaVet.service.VeterinariaService;
import com.AgendaVet.AgendaVet.service.ServicioService;
import com.AgendaVet.AgendaVet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private VeterinariaService veterinariaService;
    
    @Autowired
    private ServicioService servicioService;
    
    @Autowired
    private MascotaService mascotaService;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public Reserva save(Reserva reserva) {
        if (reserva.getVeterinariaId() != null) {
            Veterinaria veterinaria = veterinariaService.findById(reserva.getVeterinariaId());
            if (veterinaria == null) {
                throw new IllegalArgumentException("Veterinaria con id " + reserva.getVeterinariaId() + " no encontrada");
            }
            reserva.setVeterinaria(veterinaria);
        }
        
        if (reserva.getServicioId() != null) {
            Servicio servicio = servicioService.findById(reserva.getServicioId());
            if (servicio == null) {
                throw new IllegalArgumentException("Servicio con id " + reserva.getServicioId() + " no encontrado");
            }
            reserva.setServicio(servicio);
        }
        
        if (reserva.getMascotaId() != null) {
            Mascota mascota = mascotaService.findById(reserva.getMascotaId());
            if (mascota == null) {
                throw new IllegalArgumentException("Mascota con id " + reserva.getMascotaId() + " no encontrada");
            }
            reserva.setMascota(mascota);
        }
        
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public List<Reserva> findByVeterinariaId(Long veterinariaId) {
        return reservaRepository.findByVeterinaria_Id(veterinariaId);
    }

    @Override
    public Reserva updateEstado(Long id, String estado) {
        Reserva reserva = findById(id);
        if (reserva != null) {
            reserva.setEstado(estado);
            return save(reserva);
        }
        return null;
    }
}