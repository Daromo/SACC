package com.cfm.sacc.socios.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.cfm.sacc.socios.model.PorcentajeSocioRep;
import com.cfm.sacc.socios.model.Socio;

public interface ISocioService {
	List<Socio> getAllSocios();
	List<Socio> getSociosActivos();
	List<PorcentajeSocioRep> getAllPorcentajes();
	HttpStatus addPorcentaje();
}
