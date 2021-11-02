package com.cfm.sacc.clientes.model;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cliente {
	private String rfc;
	private Integer regimenFiscalId;
	private Map<String,Object> regimenFiscal;
	private String nombreCliente;
	private String apPaternoCliente;
	private String apMaternoCliente;
	private String razonSocial;
	private Date fechaIngreso;
	private Date lastUpdate;
	private Character status;
	private String telefono1;
	private String telefono2;
	private String correo;
	private String correoAlternativo;
	private String domicilio;
	private String codigoPostal;
}
