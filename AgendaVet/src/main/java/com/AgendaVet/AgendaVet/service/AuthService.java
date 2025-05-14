package com.AgendaVet.AgendaVet.service;

import com.AgendaVet.AgendaVet.model.Usuario;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(Usuario usuario);
    ResponseEntity<?> register(Usuario usuario);
    ResponseEntity<?> validateToken(String token);
}