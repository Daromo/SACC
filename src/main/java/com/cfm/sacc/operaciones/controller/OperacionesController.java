package com.cfm.sacc.operaciones.controller;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.jreports.client.IJReportsGenerator;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.ReciboHonorarioContabilidad;
import com.cfm.sacc.operaciones.service.IOperacionesServices;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;
import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/operaciones")
public class OperacionesController {
	
	@Autowired
	IClienteService clientesService;
	
	@Autowired
	IOperacionesServices operacionesService;
	
	@Autowired
	IJReportsGenerator jasperReportsGenerator;
	
	@Autowired
	ModelMapper modelMapper;
	
	/**
	 * RENDERIZA EL FORMULARIO PARA GENERAR EL RECIBO DE HONORARIO POR EL SERVCIO DE CONTABILIDAD
	 */
	@GetMapping("/recibo-honorario")
	public String renderFormRecibo(ReciboHonorarioContabilidad reciboHonorario, Model model) {
		//LISTA DE CLIENTES PARA RENDERIZAR EN EL MODAL
		List<Cliente> lista = clientesService.getClientesActivos();
		model.addAttribute("clientes", lista);
		return "operaciones/formGenerarRecibo";
	}
	
	/**
	 * GENERAR EL REVISO & ALMACENAR DATOS EN DB
	 */
	@PostMapping(value = "/generar-recibo", params = "add")
	public ResponseEntity<byte[]> guardar(ReciboHonorarioContabilidad reciboHonorario) throws FileNotFoundException, JRException {
		List<ReciboHonorarioContabilidad> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "HONORARIOS");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
	
	/**
	 * VISTA PREVIA DEL RECIBO DEL HONORARIO
	 * NO SE GUARDA EL REGISTRO EN DB
	 */
	@PostMapping(value = "/generar-recibo", params = "view")
	public ResponseEntity<byte[]> vistaPrevia (ReciboHonorarioContabilidad reciboHonorario) throws FileNotFoundException, JRException {
		List<ReciboHonorarioContabilidad> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "Vista previa - Documento sin validez");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
	
	/**
	 * RENDERIZAR EL FORMULARIO PARA REGISTRAR EL PAGO DE HONORARIOS 
	 */
	@GetMapping("/registrar-pago-honorario")
	public String renderFormRegistrarPago(Pago pago, Model model) {
		model.addAttribute("clientes", clientesService.getClientesActivos());
		return "operaciones/formRegistrarPago";
	}
	
	/**
	 * GUARDAR REGISTROS DEL PAGO EN DB
	 */
	@PostMapping("/guardar")
	public String addPago(Pago pago, RedirectAttributes redirectAttributes, Model model) throws JsonProcessingException {
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addPago"+Parseador.objectToJson(uid, pago));
		ResponseEntity<String> response = operacionesService.addPago(pago);
		if(response.getStatusCode() == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("success", "Registro guardado con éxito.");
			return "redirect:/operaciones/registrar-pago-honorario";
		}else {
			model.addAttribute("clientes", clientesService.getClientesActivos());
			model.addAttribute("error", response.getBody());
			return "operaciones/formRegistrarPago";
		}
	}
	
	/**
	 * GENERAR EL RECIBO & ALMACENAR DATOS EN DB
	 */
	@PostMapping(value = "/guardar", params = "add")
	public ResponseEntity<byte[]> generarReciboHonorarios(Pago pago) throws FileNotFoundException, JRException {
		ReciboHonorarioContabilidad reciboHonorario = modelMapper.map(pago, ReciboHonorarioContabilidad.class);
		List<ReciboHonorarioContabilidad> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "HONORARIOS");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
	
	/**
	 * VISTA PREVIA DEL RECIBO DEL HONORARIO
	 * NO SE GUARDA EL REGISTRO EN DB
	 */
	@PostMapping(value = "/guardar", params = "view")
	public ResponseEntity<byte[]> vistaPreviaReciboHonorarios(Pago pago) throws FileNotFoundException, JRException {
		ReciboHonorarioContabilidad reciboHonorario = modelMapper.map(pago, ReciboHonorarioContabilidad.class);
		List<ReciboHonorarioContabilidad> recibo = Arrays.asList(reciboHonorario);
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "Vista previa - Documento sin validez");
		
		return jasperReportsGenerator.generarReciboHonorario(recibo, params);
	}
	
	
	/**
	 * EndPoint PARA OBTENER LOS PERIODOS DE PAGO DE LOS CLIENTES
	 */
	@GetMapping("/periodos/{clienteRFC}")
	public ResponseEntity<Object> getPeriodos(@PathVariable String clienteRFC, Model model){
		List<Periodo> lista = operacionesService.getPeridosByCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getPeridos"+Parseador.objectToJson(uid, lista));
		model.addAttribute("periodos", lista);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	/**
	 * CATALOGOS OPERACIONES
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("listaConceptosPago", operacionesService.getConceptosPago());
		model.addAttribute("listaTarifas", operacionesService.getTarifas());
		model.addAttribute("listaMetodosPago", operacionesService.getMetodosPago());
		model.addAttribute("listaFormaPago", operacionesService.getFormasPago());
		model.addAttribute("listaBancos", operacionesService.getBancosEmisor());
		model.addAttribute("listaTiposHonorario", operacionesService.getTiposHonorarios());
	}
}
