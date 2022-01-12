package com.cfm.sacc.operaciones.service;

import java.util.List;

import com.cfm.sacc.operaciones.model.BancoEmisor;
import com.cfm.sacc.operaciones.model.ConceptoPago;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.MetodoPago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.Tarifa;
import com.cfm.sacc.operaciones.model.TipoHonorario;

public interface IOperacionesServices {
	List<ConceptoPago> getConceptosPago();
	List<TipoHonorario> getTiposHonorarios();
	List<Tarifa> getTarifas();
	List<FormaPago> getFormasPago();
	List<BancoEmisor> getBancosEmisor();
	List<MetodoPago> getMetodosPago();
	
	List<Periodo> getPeridosByCliente(String clienteRFC);
}
