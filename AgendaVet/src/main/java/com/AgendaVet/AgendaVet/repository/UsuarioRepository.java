package com.AgendaVet.AgendaVet.repository;

import com.AgendaVet.AgendaVet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // You can add custom query methods here if needed
}