package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
    Usuario patchUsuario(Long id, Usuario usuario);
}