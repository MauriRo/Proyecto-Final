package com.mauricio.authorizacion.server;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mauricio.authorizacion.dto.UsuarioRequest;
import com.mauricio.authorizacion.dto.UsuarioResponse;
import com.mauricio.authorizacion.models.Rol;
import com.mauricio.authorizacion.models.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Rol> roles = new HashMap<>();

    // Inicializamos roles y admin por defecto
    {
        Rol adminRol = new Rol();
        adminRol.setNombre("ROLE_ADMIN");
        roles.put("ROLE_ADMIN", adminRol);

        Rol userRol = new Rol();
        userRol.setNombre("ROLE_USER");
        roles.put("ROLE_USER", userRol);

        // Usuario admin por defecto
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Set.of(adminRol));
        usuarios.put("admin", admin);
    }

    @Override
    public Set<UsuarioResponse> listarUsuario() {
        return usuarios.values().stream()
                .map(u -> new UsuarioResponse(
                        u.getUsername(),
                        u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
                ))
                .collect(Collectors.toSet());
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        if (usuarios.containsKey(request.username())) {
            throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
        }

        Set<Rol> userRoles = request.roles().stream()
                .map(r -> roles.getOrDefault(r, new Rol() {{ setNombre(r); }}))
                .collect(Collectors.toSet());

        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.username());
        nuevo.setPassword(passwordEncoder.encode(request.password()));
        nuevo.setRoles(userRoles);

        usuarios.put(nuevo.getUsername(), nuevo);

        return new UsuarioResponse(
                nuevo.getUsername(),
                nuevo.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
        );
    }

    @Override
    public UsuarioResponse eliminarUsuario(String username) {
        Usuario u = usuarios.remove(username);
        if (u == null) throw new NoSuchElementException("No se encontró el usuario: " + username);
        return new UsuarioResponse(
                u.getUsername(),
                u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
        );
    }

    // Método extra para obtener usuario por username (para AuthServiceImpl)
    public Usuario getUsuario(String username) {
        return usuarios.get(username);
    }

    // Método extra para validar password
    public boolean checkPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }
}
