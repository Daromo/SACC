package com.cfm.sacc.socios.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Porcentaje {
	private String clavePorcentaje;
	private String rfcSocio;
	private Integer cantidadPorcentaje;
	private Date lastUpdate;
}
