package com.cfm.sacc.controller;

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

import com.cfm.sacc.model.Cliente;
import com.cfm.sacc.service.IClienteService;

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
		Cliente[] lista = restTemplate.getForObject("http://localhost:8080/clientes/activos", Cliente[].class);
		List<Cliente> listaClientes = Arrays.asList(lista);
		model.addAttribute("clientes",listaClientes);
		return "clientes/listClientes";
	}
}
