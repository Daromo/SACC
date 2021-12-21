package com.cfm.sacc.jreports.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.operaciones.model.ReciboHonorarioContabilidad;

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
	public ResponseEntity<byte[]> generarReciboHonorario(List<ReciboHonorarioContabilidad> listReciboHonorario,
			HashMap<String, Object> params) throws FileNotFoundException, JRException {
		
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report_templates/recibo_honorario_contabilidad.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listReciboHonorario);
		JasperPrint print = JasperFillManager.fillReport(compileReport, params, dataSource);
		
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=recibo_honorario.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}

}
