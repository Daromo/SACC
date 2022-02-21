package com.cfm.sacc.jreports.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.ReciboHonorario;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JReportsGenerator implements IJReportsGenerator {

	@Override
	public ResponseEntity<byte[]> generarReciboHonorario(List<ReciboHonorario> listRecibo, 
			String documentTitle, String contentDisposition) throws FileNotFoundException, JRException {
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", documentTitle);
		
		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario_contabilidad.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRecibo);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}

	@Override
	public void descargarRecibiHonorario(List<ReciboHonorario> listRecibo, String fileName, HttpServletResponse response) 
			throws JRException, IOException {
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "HONORARIOS");
		
		try {
			//compilar el archivo para generar el archivo .jasper
			JasperReport compileReport = JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario_contabilidad.jrxml"));
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRecibo);
			JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
			
		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "attachment;filename="+fileName+".pdf");
		    JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
		    
            response.getOutputStream().flush();
            response.getOutputStream().close();
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
		}
		
	}

	@Override
	public ResponseEntity<byte[]> reportePagosFormaPago(List<Pago> listaPagos, String startDate, String endDate, 
			String formaPago) throws FileNotFoundException, JRException {
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("START_DATE", startDate);
		params.put("END_DATE", endDate);
		params.put("FORMA_PAGO", formaPago);
		
		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/report_templates/reporte_pagos_forma_pago.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaPagos);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "filename;filename=reporte_forma_pago.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}

	@Override
	public ResponseEntity<byte[]> reportePagosTipoHonorario(List<Pago> listaPagos, String startDate, String endDate,
			String nombreTipoHonorario) throws FileNotFoundException, JRException {
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("START_DATE", startDate);
		params.put("END_DATE", endDate);
		params.put("TIPO_HONORARIO", nombreTipoHonorario);
		
		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/report_templates/reporte_pagos_tipo_honorario.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaPagos);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "filename;filename=reporte_tipo_honorario.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}
}
