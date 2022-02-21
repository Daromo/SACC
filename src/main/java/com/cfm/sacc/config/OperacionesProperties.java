package com.cfm.sacc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class OperacionesProperties {
	
	@Value("${operaciones.catalogo.conceptos.pago.url}")
	String urlConceptosPago;
	
	@Value("${operaciones.catalogo.tipos.honorarios.url}")
	String urlTiposHonorarios;
	
	@Value("${operaciones.catalogo.formas.pago.url}")
	String urlFormasPago;
	
	@Value("${operaciones.catalogo.bancos.emisor.url}")
	String urlBancosEmisor;
	
	@Value("${operaciones.catalogo.metodo.pago.url}")
	String urlMetodosPago;
	
	@Value("${operaciones.periodos.cliente.url}")
	String urlPeriodos;
	
	@Value("${operaciones.agregar.pago.url}")
	String urlAddPago;
	
	@Value("${operaciones.recibo.guardar.url}")
	String urlAddReciboHonorario;
	
	@Value("${numeros.letras.url}")
	String urlAPINumberToLetters;
	
	@Value("${operaciones.reportes.pagos.forma.pago}")
	String urlReportePagosFormaPago;
	
	@Value("${operaciones.reportes.pagos.tipo.honorario}")
	String urlReportePagosTipoHonorario;
	
	@Value("${operaciones.pagos.cliente.url}")
	String urlHistorialPagos;
}
