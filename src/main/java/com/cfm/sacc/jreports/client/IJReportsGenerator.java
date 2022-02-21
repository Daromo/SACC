package com.cfm.sacc.jreports.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.ReciboHonorario;

import net.sf.jasperreports.engine.JRException;

public interface IJReportsGenerator {
	ResponseEntity<byte[]> generarReciboHonorario(List<ReciboHonorario> recibo, String documentTitle, String contentDisposition) 
			throws FileNotFoundException, JRException;
	void descargarRecibiHonorario(List<ReciboHonorario> recibo, String fileName, HttpServletResponse response) 
			throws JRException, IOException;
	
	ResponseEntity<byte[]> reportePagosFormaPago(List<Pago> listaPagos, String startDate, String endDate, String nombreFormaPago) 
			throws FileNotFoundException, JRException;
	
	ResponseEntity<byte[]> reportePagosTipoHonorario(List<Pago> listaPagos, String startDate, String endDate, String nombreTipoHonorario) 
			throws FileNotFoundException, JRException;
}
