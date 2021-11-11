package com.cfm.sacc.socios.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//Obtener los registros de los socios desde el servicio
	@GetMapping("/activos")
	public String renderListSocios(Model model) {
		List<Socio> listaSocios = socioService.getListaSocios();
		model.addAttribute("socios", listaSocios);
		return "socios/listSocios";
	}
	
	//RENDERIZAR EL FORMULARIO DE PORCENTAJES
	@GetMapping("/porcentajes")
	public String renderFormPorcentajes(Porcentaje porcentaje, Model model) {
		List<Socio> listaSocios = socioService.getListaSocios();
		model.addAttribute("socios", listaSocios);
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * METODO PARA AGREGAR EL PORCENTAJE A LA TABLA DE DETALLES
	 */
	@GetMapping("/porcentaje/agregar/detalle")
	public String agregarDetallePorcentaje(Porcentaje porcentaje, Model model, RedirectAttributes redirectAtrAttributes) {
		for(Porcentaje item : detallesPorcentajes) {
			if (porcentaje.getRfcSocio().equals(item.getRfcSocio())) {
				redirectAtrAttributes.addFlashAttribute("settings", "¡El cliente ya existe!");
				return "redirect:/socios/porcentajes";
			}
		}
		this.detallesPorcentajes.add(porcentaje);
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * METODO PARA ELIMINAR PORCENTAJE DESDE LA LISTA 
	 */
	@GetMapping("/porcentaje/remover/{rfc}")
	public String removerDetallePorcentaje(@PathVariable String rfc,Porcentaje porcentaje, Model model) {
		if(detallesPorcentajes.removeIf(item -> item.getRfcSocio().equals(rfc)))
			model.addAttribute("listaPorcentajes", detallesPorcentajes);
		return PATH_FORM_PORCENTAJES;
	}
	
	@GetMapping("/porcentaje/cancelar")
	public String cancelarPorcentajes(Porcentaje porcentaje) {
		detallesPorcentajes.clear();
		return PATH_FORM_PORCENTAJES;
	}
	
	@GetMapping("/porcentaje/guardar")
	public String agregarPorcentaje() {
		return null;
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("listaPorcentajes", detallesPorcentajes);
		model.addAttribute("socios", socioService.getListaSocios());
	}
}
