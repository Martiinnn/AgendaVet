package com.AgendaVet.AgendaVet.service.impl;

import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseEntity<?> login(Usuario usuario) {
        return ResponseEntity.ok().body("Login exitoso");
    }

    @Override
    public ResponseEntity<?> register(Usuario usuario) {
        return ResponseEntity.ok().body("Registro exitoso");
    }

    @Override
    public ResponseEntity<?> validateToken(String token) {
        return ResponseEntity.ok().body("Token válido");
    }
}