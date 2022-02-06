package com.cfm.sacc.operaciones.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reporte {
	private String formaPagoId;
	private Integer tipoHonorarioId;
	private String startDate;
	private String endDate;
}
