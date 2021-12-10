package com.cfm.sacc.operaciones.controller;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.jreports.client.IJReportsGenerator;
import com.cfm.sacc.operaciones.model.ReciboHonorario;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/operaciones")
public class OperacionesController {
	
	@Autowired
	IClienteService clientesService;
	
	@Autowired
	IJReportsGenerator jasperReportsGenerator;
	
	/*
	 * RENDERIZA EL FORMULARIO PARA GENERAR EL RECIBO DE HONORARIO
	 */
	@GetMapping("/recibo-honorario")
	public String renderFormRecibo(ReciboHonorario reciboHonorario, Model model) {
		List<Cliente> lista = clientesService.getClientesActivos();
		model.addAttribute("clientes", lista);
		return "operaciones/formGenerarRecibo";
	}
	
	@PostMapping(value = "/generar-recibo", params = "add")
	public ResponseEntity<byte[]> guardar(ReciboHonorario reciboHonorario) throws FileNotFoundException, JRException {
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "HONORARIOS");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
	
	
	/*
	 * VISTA PREVIA DEL RECIBO DEL HONORARIO
	 * NO SE GUARDA EL REGISTRO EN DB
	 */
	@PostMapping(value = "/generar-recibo", params = "view")
	public ResponseEntity<byte[]> vistaPrevia (ReciboHonorario reciboHonorario) throws FileNotFoundException, JRException {
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "Vista previa - Documento sin validez");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
}
