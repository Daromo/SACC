package com.cfm.sacc.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.model.RegimenFiscal;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;

@Controller
@RequestMapping(value="/clientes")
public class ClientesController {	
	@Autowired
	IClienteService clientesService;
	
	//LISTAR CLIENTES ACTIVOS
	@GetMapping("/activos")
	public String getClientes(Model model){		
		List<Cliente> lista = clientesService.getClientesActivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesActivos"+Parseador.objectToJson(uid, lista));
		model.addAttribute("clientes", lista);
		return "clientes/listClientes";
	}
	
	// LISTAR CLIENTES INACTIVOS
	@GetMapping("/inactivos")
	public String getClienteInactivos(Model model) {
		List<Cliente> lista = clientesService.getClientesInactivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesInactivos"+Parseador.objectToJson(uid, lista));
		model.addAttribute("clientes", lista);
		return "clientes/reactivarCliente";
	}
	
	@GetMapping("/view/{rfc}")
	public String verDetalle(@PathVariable("rfc") String clienteRFC, Model model) {
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "verDetalle"+Parseador.objectToJson(uid, cliente));
		model.addAttribute("cliente", cliente);
		return "clientes/detalleCliente";
	}
	
	@GetMapping("/baja/{rfc}")
	public String bajaCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.bajaCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "bajaCliente "+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK)		
			redirectAttributes.addFlashAttribute("success", "Cliente dado de baja");
		return "redirect:/clientes/inactivos/";
	}
	
	@GetMapping("/reactivar/{rfc}")
	public String reactivarCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.reactivarCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "reactivarCliente "+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("success", "Status modificado");
			return "redirect:/clientes/activos/";
		}else
			return null;
	}
	
	@GetMapping("/nuevo")
	public String renderFormNewCliente(Cliente cliente, Model model){
		//OBTENEMOS EL CATALOGO DE LOS REGIMENES FISCALES PARA LLENAR EL SELECT EN EL FRONT
		List<RegimenFiscal> lista = clientesService.getRegimenFiscal();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getListaRegimen"+Parseador.objectToJson(uid, lista));
		model.addAttribute("titulo", "Nuevo Cliente");
		model.addAttribute("listaRegimen", lista);
		if(lista.isEmpty())
			model.addAttribute("error", "Error para obtener la lista de regimen fiscal, por favor intente mas tarde");
		return "clientes/formCliente";
	}
	
	@GetMapping("/edit/{rfc}")
	public String renderFormEditCliente(@PathVariable("rfc") String clienteRFC, Model model){
		//OBTENEMOS EL CATALOGO DE LOS REGIMENES FISCALES PARA LLENAR EL SELECT EN EL FRONT
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getListaRegimen"+Parseador.objectToJson(uid, cliente));
		List<RegimenFiscal> lista = clientesService.getRegimenFiscal();

		model.addAttribute("listaRegimen", lista);
		model.addAttribute("titulo", "Modificar Cliente");
		model.addAttribute("cliente", cliente);
		return "clientes/formCliente";
	}
	
	@PostMapping("/guardar")
	public String addCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
		//SPRING BOOT REALIZA EL DATA BINDING POR MEDIO DEL NAME DE LOS INPUT'S
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "Agreagar Nuevo cliente"+Parseador.objectToJson(uid, cliente));
		HttpStatus statusCode = clientesService.addCliente(cliente);
		if(statusCode == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("success", "Registro almacenado");
			return "redirect:/clientes/activos/";
		}
		return null;
	}
	
}