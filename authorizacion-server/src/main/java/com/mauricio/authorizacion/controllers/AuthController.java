package com.mauricio.authorizacion.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mauricio.authorizacion.dto.LoginRequest;
import com.mauricio.authorizacion.dto.UsuarioRequest;
import com.mauricio.authorizacion.dto.UsuarioResponse;
import com.mauricio.authorizacion.server.AuthService;
import com.mauricio.authorizacion.server.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> authenticate(@Valid @RequestBody LoginRequest request) throws Exception {
        String token = authService.authenticate(request.username(), request.password());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/login")
    public ResponseEntity<Set<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuario());
    }

    @PostMapping("/admin/usuarios") public ResponseEntity<UsuarioResponse> crearUsuarios(@Valid @RequestBody UsuarioRequest request)
    { return ResponseEntity.ok(usuarioService.crearUsuario(request)); }

    // 🔹 Métodos administrativos protegidos por rol ADMIN
    @GetMapping("/admin/usuarios/{username}")
    public ResponseEntity<UsuarioResponse> eliminarUsuario(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.eliminarUsuario(username));
    }

    @GetMapping("/api/me")
    public ResponseEntity<Map<String, Object>> me(org.springframework.security.core.Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("roles", authentication.getAuthorities().stream()
                .map(ga -> ga.getAuthority())
                .toList()
        );
        return ResponseEntity.ok(response);
    }

}
