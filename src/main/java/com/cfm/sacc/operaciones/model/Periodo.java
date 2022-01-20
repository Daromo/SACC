package com.cfm.sacc.operaciones.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Periodo {
	private Integer id;
	private String clienteRFC;
	private Integer ejercicio;
	private Integer mes;
	private Integer diaPago;
	private Date fechaCorte;
	private Character statusPago;
	private Date fechaRegistro;
	private Date fechaPago;
}
