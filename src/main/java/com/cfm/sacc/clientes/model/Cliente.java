package com.cfm.sacc.clientes.model;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
	private String rfc;
	//SE UTILIZA MAP PARA REALIZAR EL MAPEO DEL JSON
	private Map<String, Object> regimenFiscal;
	private String nombreCliente;
	private String apPaternoCliente;
	private String apMaternoCliente;
	private String razonSocial;
	private Date fechaIngreso;
	private Date lastUpdate;
	private char status;
	private String telefono1;
	private String telefono2;
	private String correo;
	private String correoAlternativo;
	private String domicilio;
	private String codigoPostal;
}
