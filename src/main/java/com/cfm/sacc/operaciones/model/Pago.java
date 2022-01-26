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
	// Necesario para validar reglas negocio
	private String clienteRFC;
	private Integer periodoId;
	private Integer conceptoPagoId;
	private Integer tipoHonorarioId;
	private String formaPagoId;
	private String bancoEmisorId = "999";
	private String folioCheque;
	private Integer metodoPagoId;
	private String folioReciboFactura;
	private Date fechaRegistro;
	private Float importe;
}
