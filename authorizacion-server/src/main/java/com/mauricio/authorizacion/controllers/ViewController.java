package com.mauricio.authorizacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	  @GetMapping("/login")
	    public String login() {
	        return "login"; // Thymeleaf buscará login.html
	    }
}
