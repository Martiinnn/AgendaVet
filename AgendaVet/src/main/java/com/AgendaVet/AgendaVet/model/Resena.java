package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "resenas")
@Data
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String comentario;
    private Integer puntuacion;
    private String fecha;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "veterinaria_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Veterinaria veterinaria;

    public Integer getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    public void setUsuarioId(Integer usuarioId) {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setId(usuarioId);
    }

    public Integer getVeterinariaId() {
        return veterinaria != null ? veterinaria.getId() : null;
    }

    public void setVeterinariaId(Integer veterinariaId) {
        if (veterinaria == null) {
            veterinaria = new Veterinaria();
        }
        veterinaria.setId(veterinariaId);
    }
}