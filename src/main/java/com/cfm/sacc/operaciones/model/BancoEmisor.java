package com.cfm.sacc.operaciones.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BancoEmisor {
	private Integer id;
	private String nombre;
	private String numeroCuenta;
	private Date fechaRegistro;
	private Character status;
}
