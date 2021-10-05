package com.cfm.sacc.clientes.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;

@Controller
@RequestMapping(value="/clientes")
public class ClientesController {
	//ELIMINAR
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	IClienteService clientesService;
	
	//LISTAR TODOS LOS CLIENTES
	@GetMapping("/")
	public String getClientes(@RequestParam Map<String, Object> params,Model model){		
		List<Cliente> lista = clientesService.getClientes();
		model.addAttribute("clientes", lista);
		return "clientes/listClientes";
	}
}
