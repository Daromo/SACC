package com.cfm.sacc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class SociosProperties {
	
	@Value("${socios.activos.lista.url}")
	String urlSociosActivos;
	
	@Value("${socios.lista.url}")
	String urlAllSocios;
	
	@Value("${socios.lista.porcentajes.url}")
	String urlAllPorcentajes;
	
	@Value("${socios.agregar.porcentaje.url}")
	String urlAgregarPorcentaje;
	
	@Value("${socios.last.clave.porcentaje.url}")
	String urlLastClavePorcentaje;
}
