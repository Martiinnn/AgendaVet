package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String fecha;
    private String hora;
    private String estado;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "veterinaria_id")
    private Veterinaria veterinaria;
    
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    
    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Veterinaria getVeterinaria() {
        return veterinaria;
    }
    
    public void setVeterinaria(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }
    
    public Servicio getServicio() {
        return servicio;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public Integer getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }
    
    public void setUsuarioId(Integer usuarioId) {
    }
    
    public Integer getVeterinariaId() {
        return veterinaria != null ? veterinaria.getId() : null;
    }
    
    public void setVeterinariaId(Integer veterinariaId) {
    }
    
    public Integer getServicioId() {
        return servicio != null ? servicio.getId() : null;
    }
    
    public void setServicioId(Integer servicioId) {
    }
    
    public Long getMascotaId() {
        return mascota != null ? mascota.getId() : null;
    }
    
    public void setMascotaId(Long mascotaId) {
    }
}
