package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario_Id(Long usuarioId);

    List<Reserva> findByVeterinaria_Id(Long veterinariaId);

    void deleteByMascota(Mascota mascota);
    
    // 2 Parametros
    List<Reserva> findByUsuario_IdAndEstado(Long usuarioId, String estado);
    
    List<Reserva> findByVeterinaria_IdAndFecha(Long veterinariaId, LocalDate fecha);
    
    List<Reserva> findByUsuario_IdAndVeterinaria_Id(Long usuarioId, Long veterinariaId);
    
    List<Reserva> findByMascota_IdAndEstado(Long mascotaId, String estado);    
    
}