package com.cfm.sacc.operaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.operaciones.model.ReciboHonorario;

@Controller
@RequestMapping("/operaciones")
public class OperacionesController {
	
	@Autowired
	IClienteService clientesService;
	
	@GetMapping("/generar-recibo")
	public String renderFormRecibo(ReciboHonorario reciboHonorario, Model model) {
		List<Cliente> lista = clientesService.getClientesActivos();
		model.addAttribute("clientes", lista);
		return "operaciones/formGenerarRecibo";
	}
	
	@PostMapping("/recibo/guardar")
	public String guardar(ReciboHonorario reciboHonorario) {
		return "operaciones/formGenerarRecibo";
	}
}
