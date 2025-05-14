package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario propietario;
    
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
}
