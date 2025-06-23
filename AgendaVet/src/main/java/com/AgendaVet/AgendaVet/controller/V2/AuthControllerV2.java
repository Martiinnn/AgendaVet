package com.AgendaVet.AgendaVet.controller.V2;

import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = "Autenticación V2", description = "API V2 para autenticación con HATEOAS")
public class AuthControllerV2 {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        ResponseEntity<?> response = authService.login(usuario);
        
        return response;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        ResponseEntity<?> response = authService.register(usuario);
        
        return response;
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token de autenticación")
    @ApiResponse(responseCode = "200", description = "Token válido")
    public ResponseEntity<?> validateToken(
            @Parameter(description = "Token de autorización") @RequestHeader("Authorization") String token) {
        ResponseEntity<?> response = authService.validateToken(token);
        return response;
    }
}