package com.cfm.sacc.jreports.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cfm.sacc.operaciones.model.ReciboHonorario;

import net.sf.jasperreports.engine.JRException;

public interface IJReportsGenerator {
	ResponseEntity<byte[]> generarReciboHonorario(List<ReciboHonorario> recibo, String documentTitle, String contentDisposition) throws FileNotFoundException, JRException;
	void generatedPDF(List<ReciboHonorario> recibo, String fileName) throws JRException, IOException;
}
