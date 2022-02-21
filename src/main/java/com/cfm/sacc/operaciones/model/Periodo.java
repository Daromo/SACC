package com.cfm.sacc.operaciones.model;

import java.util.Date;

import com.cfm.sacc.clientes.model.Cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Periodo {
	private Integer id;
	private Cliente cliente;
	private Integer ejercicio;
	private Integer mes;
	private Integer diaPago;
	private Date fechaCorte;
	private Character statusPeriodoPago;
	private Date fechaRegistro;
	private Date fechaModificacion;
}
