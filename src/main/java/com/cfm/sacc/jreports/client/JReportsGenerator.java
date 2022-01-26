package com.cfm.sacc.jreports.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.operaciones.model.ReciboHonorario;

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
		
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario_contabilidad.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRecibo);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}

	@Override
	public void generatedPDF(List<ReciboHonorario> listRecibo, String fileName) throws JRException, IOException {
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("title", "HONORARIOS");
		
		//compilar el archivo para generar el archivo .jasper
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario_contabilidad.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRecibo);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		JasperExportManager.exportReportToPdfFile(print, "C:\\Users\\JDani\\Downloads\\"+fileName+".pdf");
	}
}
