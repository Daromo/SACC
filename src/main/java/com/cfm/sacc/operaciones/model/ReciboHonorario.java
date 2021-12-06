package com.cfm.sacc.operaciones.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReciboHonorario {
	private String clienteRFC;
	private Double importeHonorario;
	private String importeLetra;
	private String concepto;
}
