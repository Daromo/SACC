package com.cfm.sacc.operaciones.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cfm.sacc.operaciones.model.BancoEmisor;
import com.cfm.sacc.operaciones.model.ConceptoPago;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.MetodoPago;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.ReciboHonorario;
import com.cfm.sacc.operaciones.model.TipoHonorario;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IOperacionesServices {
	List<ConceptoPago> getConceptosPago();
	List<TipoHonorario> getTiposHonorarios();
	List<FormaPago> getFormasPago();
	List<BancoEmisor> getBancosEmisor();
	List<MetodoPago> getMetodosPago();
	
	List<Periodo> getPeridosByCliente(String clienteRFC);
	ResponseEntity<String> addPago(Pago pago) throws JsonProcessingException;
	ResponseEntity<Integer> addReciboHonorario(ReciboHonorario recibo) throws JsonProcessingException;
	
	String convertNumberToLetters(Float number);
	
	List<Pago> getPagosListByFormaPago(String formaPagoId, String startDate, String endDate) throws JsonProcessingException;
	List<Pago> getPagosListByTipoHonorario(Integer tipoHonorarioId, String startDate, String endDate) throws JsonProcessingException;
	
}
