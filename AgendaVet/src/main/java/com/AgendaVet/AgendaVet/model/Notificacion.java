package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String mensaje;
    private String fechaEnvio;
    private String tipo;
    private Boolean leido;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}