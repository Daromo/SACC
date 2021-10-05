package com.cfm.sacc.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;

@Controller
@RequestMapping(value="/clientes", 
method= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ClientesController {	
	@Autowired
	IClienteService clientesService;
	
	//LISTAR TODOS LOS CLIENTES
	@GetMapping("/")
	public String getClientes(Model model){		
		List<Cliente> lista = clientesService.getClientes();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientes"+Parseador.objectToJson(uid, lista));
		model.addAttribute("clientes", lista);
		return "clientes/listClientes";
	}
	
	@PostMapping("/nuevo")
	public String addCliente() {
		return "clientes/formCliente";
	}
}