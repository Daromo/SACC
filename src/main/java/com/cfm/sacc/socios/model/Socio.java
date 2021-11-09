package com.cfm.sacc.socios.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Socio {
	private String rfc;
	private String nombreSocio;
	private String apPaternoSocio;
	private String apMaternoSocio;
	private String correo;
	private Character status;
	private Date fechaAlta;
	private Date lastUpdate;
}
