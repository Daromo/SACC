package com.cfm.sacc.clientes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;

@Controller
@RequestMapping(value="/clientes")
public class ClientesController {
	
	private static final String LISTA_CLIENTES_MODEL = "clientes";
	private static final String CLIENTE_MODEL = "cliente";
	private static final String TITULO_MODEL = "titulo";
	
	private static final String SETTINGS_FLASH = "settings";
	@Autowired
	IClienteService clientesService;
	
	//LISTAR CLIENTES ACTIVOS
	@GetMapping("/activos")
	public String getClientesActivos(Model model){		
		List<Cliente> lista = clientesService.getClientesActivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesActivos:"+Parseador.objectToJson(uid, lista));
		model.addAttribute(LISTA_CLIENTES_MODEL, lista);
		return "clientes/listClientes";
	}
	
	// LISTAR CLIENTES INACTIVOS
	@GetMapping("/inactivos")
	public String getClienteInactivos(Model model) {
		List<Cliente> lista = clientesService.getClientesInactivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesInactivos:"+Parseador.objectToJson(uid, lista));
		model.addAttribute(LISTA_CLIENTES_MODEL, lista);
		return "clientes/reactivarCliente";
	}
	
	// VER DETALLE CLIENTE
	@GetMapping("/view/{rfc}")
	public String verDetalle(@PathVariable("rfc") String clienteRFC, Model model) {
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "verDetalle:"+Parseador.objectToJson(uid, cliente));
		model.addAttribute(CLIENTE_MODEL, cliente);
		return "clientes/detalleCliente";
	}
	
	// BAJA CLIENTE
	@GetMapping("/baja/{rfc}")
	public String bajaCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.bajaCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "bajaCliente:"+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK)		
			redirectAttributes.addFlashAttribute(SETTINGS_FLASH, "Cliente dado de baja");
		return "redirect:/clientes/inactivos/";
	}
	
	// REACTIVAR CLIENTE DADO DE BAJA
	@GetMapping("/reactivar/{rfc}")
	public String reactivarCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.reactivarCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "reactivarCliente:"+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute(SETTINGS_FLASH, "Registro actualizado con éxito.");
			return "redirect:/clientes/activos/";
		}else
			return null;
	}
	
	// VISUALIZAR FORMULARIO PARA AGREGAR CLIENTE
	@GetMapping("/nuevo")
	public String renderFormNewCliente(Cliente cliente, Model model){		
		model.addAttribute(TITULO_MODEL, "Nuevo Cliente");
		return "clientes/formCliente";
	}
	
	// VISUALIZAR FORMULARIO PARA EDITAR LOS DATOS DEL CLIENTE
	@GetMapping("/edit/{rfc}")
	public String renderFormEditCliente(@PathVariable("rfc") String clienteRFC, Model model){		
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "renderFormEditCliente:"+Parseador.objectToJson(uid, cliente));
		
		model.addAttribute(TITULO_MODEL, "Modificar Cliente");
		model.addAttribute(CLIENTE_MODEL, cliente);
		return "clientes/formCliente";
	}
	
	// GUARDAR LOS DATOS DEL DATOS DEL FORMULARIO
	@PostMapping("/guardar")
	public String addCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addCliente:"+Parseador.objectToJson(uid, cliente));
		HttpStatus statusCode = clientesService.addCliente(cliente);
		if(statusCode == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute(SETTINGS_FLASH, "Registro guardado con éxito.");
			return "redirect:/clientes/activos/";
		}
		return null;
	}
	
	/**
	 * Endpoint para buscar clientes por su rfc o por el régimen fiscal
	 * el rfc y el regimen fiscal se vincunlan al objeto que se recibe como parametro
	 */
	@GetMapping("/buscar")
	public String filtrarClientes(Cliente cliente, Model model, RedirectAttributes redirectAttributes) {
		LogHandler.info(null, getClass(), "CLIENTE A BUSCAR:"+cliente);
		List<Cliente> searchListClientes = clientesService.searchCliente(cliente);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "filtrarClientes:"+Parseador.objectToJson(uid, searchListClientes));
		
		if(searchListClientes.isEmpty()) {
			redirectAttributes.addFlashAttribute("msgBusqueda", "Sin resultado para esta búsqueda");
			return "redirect:/clientes/activos";
		}
		model.addAttribute(LISTA_CLIENTES_MODEL, searchListClientes);
		return "clientes/listClientes";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Cliente clienteSearch = new Cliente();
		model.addAttribute("listaRegimen", clientesService.getRegimenFiscal());
		model.addAttribute("search",clienteSearch);
	}
	
	/**
	 * PERZONALIZAR EL DATA BINDING PARA LOS TIPO DE DATOS:
	 * Date : SE DEFINE UN FORMATO PARA LA FECHA Y SE PERMITIR CADENAS VACIAS 
	 * String: SETTEA COMO NULL LOS STRING QUE SE ENCUENTRAN VACIOS DURANTE EL DATA BINDING
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}