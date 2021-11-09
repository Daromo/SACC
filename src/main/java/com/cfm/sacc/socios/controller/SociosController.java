package com.cfm.sacc.socios.controller;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfm.sacc.socios.model.Porcentaje;

@Controller
@RequestMapping(value = "/socios")
public class SociosController {
	
	LinkedList<Porcentaje> detallesPorcentajes = new LinkedList<>();
	
	@GetMapping("/porcentajes")
	public String renderFormPorcentajes(Porcentaje porcentaje) {
		return "socios/formPorcentajes";
	}

	@GetMapping("/agregar/porcentaje")
	public String agregarDetallePorcentaje(Porcentaje porcentaje, Model model) {
		if(!detallesPorcentajes.contains(porcentaje))
			this.detallesPorcentajes.add(porcentaje);
		model.addAttribute("listaPorcentajes", detallesPorcentajes);
		return "socios/formPorcentajes";
	}
	
}
