package com.cfm.sacc.operaciones.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConceptoPago {
	private Integer id;
	private String nombre;
	private Date fechaRegistro;
	private Character status;
}
