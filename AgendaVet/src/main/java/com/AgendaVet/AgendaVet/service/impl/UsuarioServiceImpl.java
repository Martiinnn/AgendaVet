package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.repository.UsuarioRepository;
import com.AgendaVet.AgendaVet.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public Usuario patchUsuario(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id).orElse(null);
        if (existente == null) return null;

        if (usuario.getNombre() != null) existente.setNombre(usuario.getNombre());
        if (usuario.getEmail() != null) existente.setEmail(usuario.getEmail());
        if (usuario.getContrasena() != null) existente.setContrasena(usuario.getContrasena());
        if (usuario.getRol() != null) existente.setRol(usuario.getRol());
        if (usuario.getTelefono() != null) existente.setTelefono(usuario.getTelefono());
        if (usuario.getDireccion() != null) existente.setDireccion(usuario.getDireccion());

        return usuarioRepository.save(existente);
    }   
}