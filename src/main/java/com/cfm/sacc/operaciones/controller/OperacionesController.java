package com.cfm.sacc.operaciones.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
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

import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.jreports.client.IJReportsGenerator;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.ReciboHonorario;
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
	IClienteService serviceCliente;
	
	@Autowired
	IOperacionesServices serviceOperaciones;
	
	@Autowired
	IJReportsGenerator jasperReportsGenerator;
	
	@Autowired
	ModelMapper modelMapper;
	
	/**
	 * Metodo que renderiza el formulario para registrar los datos del recibo de honorario
	 */
	@GetMapping("/recibo-honorario")
	public String renderFormRecibo(ReciboHonorario reciboHonorario) {
		return "operaciones/formGenerarRecibo";
	}
	
	/**
	 * Metodo para guardar los datos del recibo de honorario sencillo en base de datos
	 * @param reciboHonorario, redirectAttributes, model
	 */
	@PostMapping(value = "/recibo-honorario", params = "add")
	public String addReciboHonorario(ReciboHonorario reciboHonorario, RedirectAttributes redirectAttributes, Model model) throws JRException, IOException {
		
		Integer idReciboHonorario;
		String fileName;
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "generarReciboHonorario"+Parseador.objectToJson(uid, reciboHonorario));
		// Recuperamos el importe en letra desde la API externa
		String importeLetra = serviceOperaciones.convertNumberToLetters(reciboHonorario.getImporte());
		if (!importeLetra.equals("null")) {
			reciboHonorario.setImporteLetra(importeLetra);
			ResponseEntity<Integer> responseAddRecibo = serviceOperaciones.addReciboHonorario(reciboHonorario);
			if(responseAddRecibo.getStatusCode() == HttpStatus.OK) {
				// Recuperamos el id del recibo para asignarlo al objeto reciboHonorario e imprimir el id
				idReciboHonorario = responseAddRecibo.getBody();
				reciboHonorario.setId(idReciboHonorario);
				// Concatenamos el id del recibo del honorario al nombre del reporte
				fileName = "recibo_honorario_" + idReciboHonorario.toString() + ".pdf";
			}else {
				// Recuperamos el mensaje del header para renderizar los errores en la vista
				String headerResponse = responseAddRecibo.getHeaders().toString();
				model.addAttribute("error", headerResponse);
				return "operaciones/formGenerarRecibo";
			}
		}else {
			throw new FileNotFoundException("Error al convertir el numero a letras");
		}
		
		//List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		//String contentDisposition = "attachment;filename=" + fileName;
		//return jasperReportsGenerator.generarReciboHonorario(recibo, "HONORARIOS", contentDisposition);
		
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		jasperReportsGenerator.generatedPDF(recibo, fileName);
		redirectAttributes.addFlashAttribute("success", "Recibo generado con exito");
		return "redirect:/operaciones/recibo-honorario";

	}
	
	/**
	 * Metodo para generar la vista previa del recibo desde el navegador
	 * @param reciboHonorario
	 */
	@PostMapping(value = "/recibo-honorario", params = "view")
	public ResponseEntity<byte[]> vistaPreviaReciboHonorario(ReciboHonorario reciboHonorario) throws JRException, IOException {
		String importeLetra = serviceOperaciones.convertNumberToLetters(reciboHonorario.getImporte());
		if (!importeLetra.equals("null")) {
			reciboHonorario.setImporteLetra(importeLetra);
		}else{
			throw new FileNotFoundException("Error al convertir el numero a letras");
		}
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		String contentDisposition = "filename;filename=recibo_vista_previa.pdf";
		return jasperReportsGenerator.generarReciboHonorario(recibo, "Vista previa - Documento sin validez", contentDisposition);
	}
	
	/**
	 * Metodo que renderiza el formulario para registrar un nuevo pago
	 * @param pago
	 */
	@GetMapping("/registrar-pago-honorario")
	public String renderFormRegistrarPago(Pago pago) {
		return "operaciones/formRegistrarPago";
	}
	
	/**
	 * Metodo para guardar el registro del pago en base de datos
	 * @param Pago
	 */
	@PostMapping("/guardar")
	public String addPago(Pago pago, RedirectAttributes redirectAttributes, Model model) throws JsonProcessingException {
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addPago"+Parseador.objectToJson(uid, pago));
		ResponseEntity<String> response = serviceOperaciones.addPago(pago);
		if(response.getStatusCode() == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("success", "Registro guardado con éxito.");
			return "redirect:/operaciones/registrar-pago-honorario";
		}else {
			model.addAttribute("error", response.getBody());
			return "operaciones/formRegistrarPago";
		}
	}
	
	/**
	 * Metodo para obtener los periodos de pago con status pendiente & activo de cada cliente
	 * @param cliente RFC
	 */
	@GetMapping("/periodos/{clienteRFC}")
	public ResponseEntity<Object> getPeriodos(@PathVariable String clienteRFC, Model model){
		List<Periodo> lista = serviceOperaciones.getPeridosByCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getPeridos"+Parseador.objectToJson(uid, lista));
		model.addAttribute("periodos", lista);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	/**
	 * Metodo que agrega los atributos generales para todos los modelos del controlador
	 * @param model
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("clientes", serviceCliente.getClientesInactivos());
		model.addAttribute("listaConceptosPago", serviceOperaciones.getConceptosPago());
		model.addAttribute("listaMetodosPago", serviceOperaciones.getMetodosPago());
		model.addAttribute("listaFormaPago", serviceOperaciones.getFormasPago());
		model.addAttribute("listaBancos", serviceOperaciones.getBancosEmisor());
		model.addAttribute("listaTiposHonorario", serviceOperaciones.getTiposHonorarios());
	}
}
