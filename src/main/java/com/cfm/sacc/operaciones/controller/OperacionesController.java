package com.cfm.sacc.operaciones.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.jreports.client.IJReportsGenerator;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.ReciboHonorario;
import com.cfm.sacc.operaciones.model.Reporte;
import com.cfm.sacc.operaciones.model.TipoHonorario;
import com.cfm.sacc.operaciones.service.IOperacionesServices;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;
import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/operaciones")
public class OperacionesController {
	
	private static final String FORM_GENERAR_RECIBO = "operaciones/formGenerarRecibo";
	private static final String FORM_REGISTRAR_PAGO = "operaciones/formRegistrarPago";
	
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
		return FORM_GENERAR_RECIBO;
	}
	
	/**
	 * Metodo para guardar los datos del recibo de honorario sencillo en base de datos
	 * @param reciboHonorario, redirectAttributes, model
	 */
	@PostMapping(value = "/recibo-honorario", params = "add")
	public String guardarReciboHonorario(ReciboHonorario reciboHonorario, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
		
		// Validacion del Data Binding de los datos de entrada con la clase de modelo 
		if (bindingResult.hasErrors()) {
			return FORM_GENERAR_RECIBO;
		}
		
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
				return FORM_GENERAR_RECIBO;
			}
		}else {
			throw new FileNotFoundException("Error al convertir el numero a letras");
		}
		
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		jasperReportsGenerator.descargarRecibiHonorario(recibo, fileName, response);
		
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
		return FORM_REGISTRAR_PAGO;
	}
	
	/**
	 * Metodo para guardar el registro del pago en base de datos
	 * @param Pago
	 */
	@PostMapping("/guardar")
	public String guardarPago(Pago pago, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) throws JsonProcessingException {
		
		// Validacion del Data Binding de los datos de entrada con la clase de modelo 
		if (bindingResult.hasErrors()) {
			return FORM_REGISTRAR_PAGO;
		}
		
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "addPago"+Parseador.objectToJson(uid, pago));
		ResponseEntity<String> response = serviceOperaciones.addPago(pago);
		if(response.getStatusCode() == HttpStatus.OK) {
			redirectAttributes.addFlashAttribute("success", "Registro guardado con éxito.");
			return "redirect:/clientes/pagos/".concat(pago.getClienteRFC());
		}else {
			model.addAttribute("error", response.getBody());
			return FORM_REGISTRAR_PAGO;
		}
	}
	
	/**
	 * Metodo para obtener los periodos de pago con status Pago Parcial, Pago Activo & Pago Vencido de cada cliente
	 * @param cliente RFC
	 */
	@GetMapping("/periodos/{clienteRFC}")
	public ResponseEntity<Object> obtenerPeriodos(@PathVariable String clienteRFC, Model model){
		List<Periodo> lista = serviceOperaciones.getPeridosByCliente(clienteRFC);
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getPeridos"+Parseador.objectToJson(uid, lista));
		model.addAttribute("periodos", lista);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	/**
	 * Metodo que renderiza el formulario para generar el reporte de pagos de acuerdo al tipo de honorario
	 * @param Reporte
	 */
	@GetMapping("/reporte/pagos/tipo-honorario")
	public String renderFormReporteTipoHonorario(Reporte reporte) {
		return "operaciones/reporteTipoHonorario";
	}
	
	/**
	 * Metodo que renderiza el formulario para generar el reporte de pagos de acuerdo a la forma de pago
	 * @param Reporte
	 */
	@GetMapping("/reporte/pagos/forma-pago")
	public String renderFormReporteFormaPago(Reporte reporte) {
		return "operaciones/reporteFormaPago";
	}
	
	/**
	 * Metodo para obtener la lista de pagos filtrada por la forma de pago & el rango de fechas definido por el usuario
	 * @param Reporte
	 * @throws JRException 
	 * @throws FileNotFoundException 
	 */
	@GetMapping("/reporteFormaPago")
	public ResponseEntity<byte[]> obtenerReportePagosFormaPago(Reporte reporte) 
			throws JsonProcessingException, FileNotFoundException, JRException {
		
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "obtenerReportePagosTipoHonorario"+Parseador.objectToJson(uid, reporte));
		String startDate = reporte.getStartDate();
		String endDAte = reporte.getEndDate();
		List<Pago> listaPagos = serviceOperaciones.getPagosListByFormaPago(reporte.getFormaPagoId(), startDate, endDAte);
		List<FormaPago> listFormaPago = serviceOperaciones.getFormasPago();
		String nombreFormaPago = listFormaPago
				.stream()
				.filter(f -> f.getId().equals(reporte.getFormaPagoId()))
				.findFirst()
				.get()
				.getNombre();
		
		return jasperReportsGenerator.reportePagosFormaPago(listaPagos, startDate, endDAte, nombreFormaPago);
	}
	
	/**
	 * Metodo para obtener la lista de pagos filtrada por el tipo de honrario & el rango de fechas definido por el usuario
	 * @param Reporte
	 * @throws JRException 
	 * @throws FileNotFoundException 
	 */
	@GetMapping("/reporteTipoHonorario")
	public ResponseEntity<byte[]> obtenerReportePagosTipoHonorario(Reporte reporte) 
			throws JsonProcessingException, FileNotFoundException, JRException {
		
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "obtenerReportePagosTipoHonorario"+Parseador.objectToJson(uid, reporte));
		String startDate = reporte.getStartDate();
		String endDAte = reporte.getEndDate();
		List<Pago> listaPagos = serviceOperaciones.getPagosListByTipoHonorario(reporte.getTipoHonorarioId(), startDate, endDAte);
		List<TipoHonorario> listTipoHonorario = serviceOperaciones.getTiposHonorarios();
		String nombreTipoHonorario = listTipoHonorario
				.stream()
				.filter(f -> f.getId().equals(reporte.getTipoHonorarioId()))
				.findFirst()
				.get()
				.getNombre();
		return jasperReportsGenerator.reportePagosTipoHonorario(listaPagos, startDate, endDAte, nombreTipoHonorario);
	}
	
	/**
	 * Metodo que agrega los atributos generales para todos los modelos del controlador
	 * @param model
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("clientes", serviceCliente.getClientesActivos());
		model.addAttribute("listaConceptosPago", serviceOperaciones.getConceptosPago());
		model.addAttribute("listaMetodosPago", serviceOperaciones.getMetodosPago());
		model.addAttribute("listaFormaPago", serviceOperaciones.getFormasPago());
		model.addAttribute("listaBancos", serviceOperaciones.getBancosEmisor());
		model.addAttribute("listaTiposHonorario", serviceOperaciones.getTiposHonorarios());
	}
}
