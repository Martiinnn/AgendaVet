package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "servicios")
@Data
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String descripcion;
    private Float precio;
    private Integer duracion;
    
    @ManyToOne
    @JoinColumn(name = "veterinaria_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Veterinaria veterinaria;
    
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
}