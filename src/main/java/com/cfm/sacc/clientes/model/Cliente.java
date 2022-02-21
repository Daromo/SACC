package com.cfm.sacc.clientes.model;

import java.util.Date;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	private String nombreLargo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone =  "America/Mexico_City")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngreso;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdate;
	
	private Character status;
	
	private String telefono1;
	
	private String telefono2;
	
	private String correo;
	
	private String correoAlternativo;
	
	private String domicilio;
	
	private Integer codigoPostal;
}
