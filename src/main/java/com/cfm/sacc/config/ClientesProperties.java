package com.cfm.sacc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class ClientesProperties {
	
	@Value("${clientes.activos.url}")
	String urlClientesActivos;
	
	@Value("${clientes.inactivos.url}")
	String urlClientesInactivos;
	
	@Value("${clientes.buscar.rfc.url}")
	String urlBuscarClienteRFC;
	
	@Value("${clientes.baja.url}")
	String urlBajaCliente;
	
	@Value("${clientes.reactivar.url}")
	String urlReactivarCliente;
	
	@Value("${clientes.lista.regimen.url}")
	String urlListaRegimenFiscales;
	
	@Value("${clientes.agregar.url}")
	String urlAgregarCliente;
	
	@Value("${clientes.modificar.url}")
	String urlModificarCliente;
	
	@Value("${clientes.buscar.url}")
	String urlBuscarCliente;
	
}
