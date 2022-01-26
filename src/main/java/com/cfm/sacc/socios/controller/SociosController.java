package com.cfm.sacc.socios.controller;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cfm.sacc.socios.model.Porcentaje;
import com.cfm.sacc.socios.service.ISocioService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/socios")
public class SociosController {
	
	private static final String PATH_FORM_PORCENTAJES = "socios/formPorcentajes";
	LinkedList<Porcentaje> detallesPorcentajes = new LinkedList<>();
	
	@Autowired
	ISocioService socioService;
	
	/*
	 * RENDERIZAR LA LISTA DE TODOS LOS SOCIOS
	 */
	@GetMapping("/activos")
	public String renderListSocios(RedirectAttributes redirectAttributes) {
		return "socios/listSocios";
	}
	
	/*
	 * RENDERIZAR LA LISTA DE TODOS LOS PORCENTAJES 
	 */
	@GetMapping("/porcentajes")
	public String renderListPorcentajes(Model model) {
		model.addAttribute("porcentajes_socios", socioService.getAllPorcentajes());
		return "socios/listPorcentajes";
	}
	
	/*
	 * RENDERIZAR EL FORMULARIO PARA AGREGAR UN NUEVO PORCENTAJE
	 * SOLO SE MUESTRAN LOS SOCIOS ACTIVOS 
	 */
	@GetMapping("/porcentajes/form")
	public String renderFormPorcentajes(Porcentaje porcentaje, Model model) {
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * METODO PARA AGREGAR EL PORCENTAJE A LA TABLA DE DETALLES
	 */
	@GetMapping("/porcentaje/agregar/detalle")
	public String agregarDetallePorcentaje(Porcentaje porcentaje, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAtrAttributes) {
		
		// Validacion del Data Binding de los datos de entrada con la clase de modelo 
		if (bindingResult.hasErrors()) {
			return PATH_FORM_PORCENTAJES;
		}
		
		for(Porcentaje item : detallesPorcentajes) {
			if (porcentaje.getSocioRFC().equals(item.getSocioRFC())) {
				redirectAtrAttributes.addFlashAttribute("settings", "¡El cliente ya existe!");
				return "redirect:/socios/porcentajes/form";
			}
		}
		this.detallesPorcentajes.add(porcentaje);
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * METODO PARA ELIMINAR EL PORCENTAJE DESDE LA TABLA
	 */
	@GetMapping("/porcentaje/remover/detalle/{rfc}")
	public String removerDetallePorcentaje(@PathVariable String rfc,Porcentaje porcentaje, Model model) {
		if(detallesPorcentajes.removeIf(item -> item.getSocioRFC().equals(rfc)))
			model.addAttribute("listaPorcentajes", detallesPorcentajes);
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * CANCELAR LOS DATOS DEL FORMULARIO
	 */
	@PostMapping(value = "/porcentaje/guardar", params = "cancelar")
	public String cancelarPorcentajes(Porcentaje porcentaje) {
		detallesPorcentajes.clear();
		return PATH_FORM_PORCENTAJES;
	}
	
	/*
	 * Metodo para guardar la lista de porcentajes en la base de datos
	 */
	@PostMapping(value = "/porcentaje/guardar", params = "add")
	public String agregarPorcentaje(RedirectAttributes redirectAttributes) throws JsonProcessingException{
		Optional<Integer> suma = detallesPorcentajes.stream().map(Porcentaje::getCantidadPorcentaje).reduce((a,b) -> a+b);
		if(!(suma.isPresent() && suma.get()==100)) {
			redirectAttributes.addFlashAttribute("settings", "Verifique los porcentajes, la suma debe ser igual a 100.");
			return "redirect:/socios/porcentajes/form";
		}
		if (socioService.addPorcentaje(detallesPorcentajes) == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro guardado con exito");
			detallesPorcentajes.clear();
		}
		return "redirect:/socios/porcentajes/form";
	}
	
	/*
	 * setGenericos SE EJECUTA ANTES QUE CUALQUIER OTRO METODO.
	 * EL CUAL COMPARTE LOS ELEMENTOS QUE SE DECLARAN DENTRO DEL MISMO.
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("listaDetallesPorcentajes", detallesPorcentajes);
		model.addAttribute("socios",socioService.getAllSocios());
		model.addAttribute("socios_activos", socioService.getSociosActivos());
	}
}
