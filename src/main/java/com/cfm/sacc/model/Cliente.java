package com.cfm.sacc.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
	private String rfc;
	private Integer idRegimenFiscal;
	private String nombreCliente;
	private String apPaternoCliente;
	private String apMaternoCliente;
	private String razonSocial;
	private Date fechaIngreso;
	private Date lastUpdate;
	private char status;
	private Integer telefono1;
	private Integer telefono2;
	private String correo;
	private String correoAlternativo;
	private String domicilio;
	private Integer codigoPostal;
}
