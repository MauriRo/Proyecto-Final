package com.mauricio.authorizacion.server;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mauricio.authorizacion.models.Usuario;
import com.mauricio.authorizacion.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetails implements UserDetailsService{

	
	private final UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("USUARIO NO ENCONTRADO EN BD"));
		
		return new User(
				usuario.getUsername(),
				usuario.getPassword(),
				usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.collect(Collectors.toSet())
				);
	}
}
