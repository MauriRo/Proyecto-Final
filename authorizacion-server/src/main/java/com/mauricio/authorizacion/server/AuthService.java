package com.mauricio.authorizacion.server;

public interface AuthService {

	String authenticate(String username,String password)throws Exception;
	
}
