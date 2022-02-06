package com.cfm.sacc.operaciones.model;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pago {
	private Integer id;
	// Necesario para validar reglas negocio
	private String clienteRFC;
	
	private Integer periodoId;
	private Map<String, Object> periodo;
	
	private Integer conceptoPagoId;
	private Map<String, Object> conceptoPago;
	
	private Integer tipoHonorarioId;
	private Map<String, Object> tipoHonorario;
	
	private String formaPagoId;
	private Map<String, Object> formaPago;
	
	private String bancoEmisorId = "999";
	private Map<String, Object> bancoEmisor;
	
	private String folioCheque;
	
	private Integer metodoPagoId;
	private Map<String, Object> metodoPago;
	
	private String folioReciboFactura;
	private Date fechaPago;
	private Float importe;
	private String comentario;
}
