package com.cfm.sacc.operaciones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReciboHonorario {
	private Integer id;
	private String clienteRFC;
	private String nombreCliente;
	private Float importe;
	private Integer periodoId;
	private String importeLetra;
	private String conceptoMes;
}
