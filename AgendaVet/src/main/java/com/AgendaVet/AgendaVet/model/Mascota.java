package com.AgendaVet.AgendaVet.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Usuario propietario;
    
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Reserva> reservas;    

    public Integer getUsuarioId() {
        return propietario != null ? propietario.getId() : null;
    }    

    public void setUsuarioId(Integer usuarioId) {
        if (propietario == null) {
            propietario = new Usuario();
        }
        propietario.setId(usuarioId);
    }
}
