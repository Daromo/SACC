package com.cfm.sacc.socios.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfm.sacc.socios.model.Porcentaje;
import com.cfm.sacc.socios.model.Socio;
import com.cfm.sacc.socios.service.ISocioService;

@Controller
@RequestMapping(value = "/socios")
public class SociosController {
	
	private static final String PATH_FORM_PORCENTAJES = "socios/formPorcentajes";
	LinkedList<Porcentaje> detallesPorcentajes = new LinkedList<>();
	
	@Autowired
	ISocioService socioService;
	
	@GetMapping("/activos")
	public String renderListSocios(Model model) {
		List<Socio> listaSocios = socioService.getListaSocios();
		model.addAttribute("socios", listaSocios);
		return "socios/listSocios";
	}
	
	@GetMapping("/porcentajes")
	public String renderFormPorcentajes(Porcentaje porcentaje) {
		return PATH_FORM_PORCENTAJES;
	}

	@GetMapping("/agregar/porcentaje")
	public String agregarDetallePorcentaje(Porcentaje porcentaje, Model model) {
		boolean socioIsPresent = false;
		for(Porcentaje item : detallesPorcentajes) {
			if (porcentaje.getRfcSocio().equals(item.getRfcSocio()))
				socioIsPresent = true;
		}
		
		if(!socioIsPresent)
			this.detallesPorcentajes.add(porcentaje);
		model.addAttribute("listaPorcentajes", detallesPorcentajes);
		return PATH_FORM_PORCENTAJES;
	}
	
	@GetMapping("/remover/porcentaje/{rfc}")
	public String removerDetallePorcentaje(@PathVariable String rfc,Porcentaje porcentaje, Model model) {
		if(detallesPorcentajes.removeIf(item -> item.getRfcSocio().equals(rfc)))
			model.addAttribute("listaPorcentajes", detallesPorcentajes);
		return PATH_FORM_PORCENTAJES;
	}
	
	@GetMapping("/porcentajes/cancelar")
	public String cancelarPorcentajes(Porcentaje porcentaje) {
		detallesPorcentajes.clear();
		return PATH_FORM_PORCENTAJES;
	}
	
	
	/*@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("socios", socioService.getListaSocios());
	}*/
}
