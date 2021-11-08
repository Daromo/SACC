package com.cfm.sacc.socios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/socios")
public class SociosController {
	
	@GetMapping("/porcentajes")
	public String nuevoPorcentaje() {
		return "socios/formPorcentajeSocios";
	}
}
