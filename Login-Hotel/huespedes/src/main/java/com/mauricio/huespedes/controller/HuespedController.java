package com.mauricio.huespedes.controller;


import org.springframework.web.bind.annotation.*;

import com.mauricio.commons.controllers.CommonController;
import com.mauricio.commons.dto.HuespedRequest;
import com.mauricio.commons.dto.HuespedResponse;
import com.mauricio.huespedes.service.HuespedService;


@RestController
@RequestMapping("/api/huespedes")
@CrossOrigin(origins = "http://localhost:4200")
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>{

	public HuespedController(HuespedService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

  
}
