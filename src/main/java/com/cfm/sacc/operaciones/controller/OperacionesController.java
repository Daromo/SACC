package com.cfm.sacc.operaciones.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.service.IClienteService;
import com.cfm.sacc.operaciones.model.ReciboHonorario;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

@Controller
@RequestMapping("/operaciones")
public class OperacionesController {
	
	@Autowired
	IClienteService clientesService;
	
	@GetMapping("/generar-recibo")
	public String renderFormRecibo(ReciboHonorario reciboHonorario, Model model) {
		List<Cliente> lista = clientesService.getClientesActivos();
		model.addAttribute("clientes", lista);
		return "operaciones/formGenerarRecibo";
	}
	
	@PostMapping("/guardar-recibo")
	public String guardar(ReciboHonorario reciboHonorario) throws FileNotFoundException, JRException {
		reciboHonorario.setId(0000);
		Map<String, Object> parameters = new  HashMap<String, Object>();
		List<ReciboHonorario> recibo = Arrays.asList(reciboHonorario);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(recibo);
		
		JasperPrint print = JasperFillManager.fillReport(compileReport, parameters, dataSource);
		JasperViewer.viewReport(print, false);
		//ERROR
		JasperExportManager.exportReportToPdfFile(print, "honorarios.pdf");
		
		return "operaciones/formGenerarRecibo";
	}
}
