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
	private Integer conceptoPagoId;
	private Integer tipoHonorarioId;
	private Integer tarifaId;
	private String formaPagoId;
	private String bancoEmisorId = "999";
	private String folioCheque;
	private Integer metodoPagoId;
	private String folioComprobante;
	private Date fechaRegistro;
}
