package com.mauricio.oauth.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mauricio.oauth.dto.UsuarioRequest;
import com.mauricio.oauth.dto.UsuarioResponse;
import com.mauricio.oauth.models.Rol;
import com.mauricio.oauth.models.Usuario;
import com.mauricio.oauth.repositories.RolRepository;
import com.mauricio.oauth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final RolRepository rolRepository;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public Set<UsuarioResponse> listarUsuarios() {
		return usuarioRepository.findAll().stream()
				.map(usuario -> new UsuarioResponse(
						usuario.getUsername(),
						usuario.getRoles().stream()
						.map(Rol::getNombre).collect(Collectors.toSet()))
				).collect(Collectors.toSet());
	}

	@Override
	public UsuarioResponse crearUsuario(UsuarioRequest request) {
		if (usuarioRepository.findByUsername(request.username()).isPresent()) {
			throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
		}
		
		Set<Rol> roles = request.roles().stream().map(rol -> rolRepository.findByNombre(rol)
				.orElseThrow(() -> new NoSuchElementException("Rol " + rol + " no encontrado"))
				).collect(Collectors.toSet());
		Usuario usuario = new Usuario();
		usuario.setUsername(request.username());
		usuario.setPassword(passwordEncoder.encode(request.username()));
		usuario.setRoles(roles);
		usuario = usuarioRepository.save(usuario);
		return new UsuarioResponse(usuario.getUsername(),
				usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
		);
	}

	@Override
	public UsuarioResponse eliminarUsuario(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> 
			new NoSuchElementException("No se encontró el usuario: " + username)
		);
		usuarioRepository.deleteById(usuario.getId());
		return new UsuarioResponse(usuario.getUsername(),
				usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
		);
	}
}
