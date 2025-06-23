package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;
    private String estado;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notificacion> notificaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "veterinaria_id")
    @JsonIgnore
    private Veterinaria veterinaria;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    @JsonIgnore
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    @JsonIgnore
    private Mascota mascota;

    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    public void setUsuarioId(Long usuarioId) {
        if (usuario == null) usuario = new Usuario();
        usuario.setId(usuarioId);
    }

    public Long getVeterinariaId() {
        return veterinaria != null ? veterinaria.getId() : null;
    }

    public void setVeterinariaId(Long veterinariaId) {
        if (veterinaria == null) veterinaria = new Veterinaria();
        veterinaria.setId(veterinariaId);
    }

    public Long getServicioId() {
        return servicio != null ? servicio.getId() : null;
    }

    public void setServicioId(Long servicioId) {
        if (servicio == null) servicio = new Servicio();
        servicio.setId(servicioId);
    }

    public Long getMascotaId() {
        return mascota != null ? mascota.getId() : null;
    }

    public void setMascotaId(Long mascotaId) {
        if (mascota == null) mascota = new Mascota();
        mascota.setId(mascotaId);
    }
}