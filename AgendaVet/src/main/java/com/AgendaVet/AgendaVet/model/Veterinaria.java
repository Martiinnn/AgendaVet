package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "veterinarias")
@Data
public class Veterinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String horario;
    
    @OneToMany(mappedBy = "veterinaria", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "veterinaria", cascade = CascadeType.ALL)
    private List<Servicio> servicios;
    
    @OneToMany(mappedBy = "veterinaria", cascade = CascadeType.ALL)
    private List<Resena> resenas;
}
