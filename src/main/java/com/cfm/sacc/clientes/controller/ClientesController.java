package com.cfm.sacc.clientes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="/clientes")
public class ClientesController {
	
	private static final String ATTRIBUTE_LISTA_CLIENTES = "clientes";
	private static final String ATTRIBUTE_MODEL_CLIENTE = "cliente";
	private static final String ATTRIBUTE_SETTINGS_FLASH = "settings";
	
	private static final String FORM_ADD_CLIENTE = "clientes/formAddCliente";
	private static final String FORM_UPDATE_CLIENTE = "clientes/formUpdateCliente";
	
	private static final String PATH_REDIRECT_CLIENTES_ACTIVOS = "redirect:/clientes/activos/";
	
	@Autowired
	IClienteService clientesService;
	
	/**
	 * Metodo para obtener todos los clientes activos
	 * @param model
	 * @return List<Cliente>
	 */
	@GetMapping("/activos")
	public String getClientesActivos(Model model){		
		List<Cliente> lista = clientesService.getClientesActivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesActivos"+Parseador.objectToJson(uid, lista));
		model.addAttribute(ATTRIBUTE_LISTA_CLIENTES, lista);
		return "clientes/listClientes";
	}
	
	/**
	 * Metodo para obtener todos los clientes inactivos
	 * @param model
	 * @return List<Cliente>
	 */
	@GetMapping("/inactivos")
	public String getClienteInactivos(Model model) {
		List<Cliente> lista = clientesService.getClientesInactivos();
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getClientesInactivos"+Parseador.objectToJson(uid, lista));
		model.addAttribute(ATTRIBUTE_LISTA_CLIENTES, lista);
		return "clientes/reactivarCliente";
	}
	
	/**
	 * Metodo que renderiza la vista con la informacion del cliente
	 * @param cliente RFC
	 */
	@GetMapping("/view/{rfc}")
	public String verDetalle(@PathVariable("rfc") String clienteRFC, Model model) {
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "verDetalle"+Parseador.objectToJson(uid, cliente));
		model.addAttribute(ATTRIBUTE_MODEL_CLIENTE, cliente);
		return "clientes/detalleCliente";
	}
	
	/**
	 * Metodo para dar de baja a un cliente activo
	 * @param cliente RFC
	 */
	@GetMapping("/baja/{rfc}")
	public String bajaCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.bajaCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "bajaCliente"+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK)		
			redirectAttributes.addFlashAttribute(ATTRIBUTE_SETTINGS_FLASH, "Cliente dado de baja");
		return "redirect:/clientes/inactivos/";
	}
	
	/**
	 * Metodo para reactivar a un cliente dado de baja
	 * @param cliente RFC
	 */
	@GetMapping("/reactivar/{rfc}")
	public String reactivarCliente(@PathVariable("rfc") String clienteRFC, RedirectAttributes redirectAttributes) {
		HttpStatus statusCode = clientesService.reactivarCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "reactivarCliente"+Parseador.objectToJson(uid, statusCode));
		if (statusCode == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute(ATTRIBUTE_SETTINGS_FLASH, "Registro actualizado con éxito.");
			return PATH_REDIRECT_CLIENTES_ACTIVOS;
		}else
			return null;
	}
	
	/**
	 * Metodo que renderiza el formulario para agregar un nuevo cliente
	 */
	@GetMapping("/nuevo")
	public String renderFormNewCliente(Cliente cliente){
		return FORM_ADD_CLIENTE;
	}
	
	/**
	 * Metodo que renderiza el formulario para editar los datos del cliente
	 * @param cliente RFC
	 */
	@GetMapping("/edit/{rfc}")
	public String renderFormEditCliente(@PathVariable("rfc") String clienteRFC, Model model){		
		Cliente cliente = clientesService.findByRFC(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "renderFormEditCliente"+Parseador.objectToJson(uid, cliente));
		
		model.addAttribute(ATTRIBUTE_MODEL_CLIENTE, cliente);
		return FORM_UPDATE_CLIENTE;
	}
	
	/**
	 * Metodo para guardar el registro del nuevo cliente en base de datos
	 * @param Cliente
	 * @throws JsonProcessingException 
	 */
	@PostMapping("/guardar")
	public String addCliente(Cliente cliente, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		// Validacion del Data Binding de los datos de entrada con la clase de modelo  
		if (bindingResult.hasErrors()) {
			return FORM_ADD_CLIENTE;
		}
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addCliente"+Parseador.objectToJson(uid, cliente));
		ResponseEntity<String> response = clientesService.guardarCliente(cliente);
		if(response.getStatusCode() == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute(ATTRIBUTE_SETTINGS_FLASH, "Registro guardado con éxito.");
			return PATH_REDIRECT_CLIENTES_ACTIVOS;
		}else {
			model.addAttribute("error", response.getBody());
			return FORM_ADD_CLIENTE;
		}
	}
	
	
	/**
	 * Metodo para modificar los datos de un cliente
	 * @param Cliente
	 * @throws JsonProcessingException 
	 */
	@PostMapping("/actualizar")
	public String modificarCliente(Cliente cliente, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		// Validacion del Data Binding de los datos de entrada con la clase de modelo  
		if (bindingResult.hasErrors()) {
			return FORM_UPDATE_CLIENTE;
		}
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addCliente:"+Parseador.objectToJson(uid, cliente));
		ResponseEntity<String> response = clientesService.guardarCliente(cliente);
		if(response.getStatusCode() == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute(ATTRIBUTE_SETTINGS_FLASH, "Registro actualizado con éxito.");
			return PATH_REDIRECT_CLIENTES_ACTIVOS;
		}else {
			model.addAttribute("error", response.getBody());
			return FORM_UPDATE_CLIENTE;
		}
	}
	
	/**
	 * Metodo para buscar cliente(s) de acuerdo a su rfc o por el régimen fiscal al que pertenecen
	 * @param Cliente
	 */
	@GetMapping("/buscar")
	public String filtrarClientes(Cliente cliente, Model model, RedirectAttributes redirectAttributes) {
		LogHandler.info(null, getClass(), "CLIENTE A BUSCAR:"+cliente);
		List<Cliente> searchListClientes = clientesService.searchCliente(cliente);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "filtrarClientes:"+Parseador.objectToJson(uid, searchListClientes));
		
		if(searchListClientes.isEmpty()) {
			redirectAttributes.addFlashAttribute("msgBusqueda", "Sin resultado para esta búsqueda");
			return PATH_REDIRECT_CLIENTES_ACTIVOS;
		}
		model.addAttribute(ATTRIBUTE_LISTA_CLIENTES, searchListClientes);
		return "clientes/listClientes";
	}
	
	/**
	 * Metodo que agrega los atributos generales para todos los modelos del controlador
	 * @param model
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		Cliente clienteSearch = new Cliente();
		model.addAttribute("listaRegimen", clientesService.getRegimenFiscal());
		model.addAttribute("search",clienteSearch);
	}
	
	/**
	 * Metodo para personalizar el data binding:
	 * Date : Se define un formato para la fecha & se establece la propiedad que acepta valores nulos 
	 * String: Settea como nulos los argumentos de tipo String que se vincules en el Data Binding
	 * @param WebDataBinders
	 */ 
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}