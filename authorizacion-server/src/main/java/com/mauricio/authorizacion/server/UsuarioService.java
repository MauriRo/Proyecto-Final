package com.mauricio.authorizacion.server;

import java.util.Set;

import com.mauricio.authorizacion.dto.UsuarioRequest;
import com.mauricio.authorizacion.dto.UsuarioResponse;

public interface UsuarioService {

	Set<UsuarioResponse> listarUsuario();
	
	UsuarioResponse crearUsuario(UsuarioRequest request);
	
	UsuarioResponse eliminarUsuario(String username);
}
