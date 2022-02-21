package com.cfm.sacc.operaciones.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pago {
	private Integer id;
	
	private String clienteRFC;
	
	private Integer periodoId;
	private Periodo periodo;
	
	private Integer conceptoPagoId;
	private ConceptoPago conceptoPago;
	
	private Integer tipoHonorarioId;
	private TipoHonorario tipoHonorario;
	
	private String formaPagoId;
	private FormaPago formaPago;
	
	private String bancoEmisorId = "999";
	private BancoEmisor bancoEmisor;
	
	private String folioCheque;
	
	private Integer metodoPagoId;
	private MetodoPago metodoPago;
	
	private String folioReciboFactura;
	private Date fechaPago;
	private Float importe;
	private String comentario;
}
