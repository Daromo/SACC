package com.cfm.sacc.jreports.client;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cfm.sacc.operaciones.model.ReciboHonorario;

import net.sf.jasperreports.engine.JRException;

public interface IJReportsGenerator {
	ResponseEntity<byte[]> generarReciboHonorario(List<ReciboHonorario> reciboHonorario, 
			HashMap<String, Object> params) throws FileNotFoundException, JRException;
}
