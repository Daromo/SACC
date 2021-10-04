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

@Controller
@RequestMapping(value="/clientes")
public class ClientesController {
	
	@Autowired
	RestTemplate restTemplate;
	
	//LISTAR TODOS LOS CLIENTES
	@GetMapping("/")
	public String getClientes(@RequestParam Map<String, Object> params,Model model){		
		Cliente[] lista = restTemplate.getForObject("http://localhost:8080/clientes/", Cliente[].class);
		List<Cliente> listaClientes = Arrays.asList(lista);
		
		/*int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		PageRequest pageRequest = PageRequest.of(page,2);
		
		int totalPages = listaClientes.size()-1;
		
		if (totalPages > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("totalPages", pages);
		}*/
		
		model.addAttribute("clientes",listaClientes);
		return "clientes/listClientes";
	}
	
}
