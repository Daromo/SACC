package com.cfm.sacc.operaciones.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.operaciones.model.BancoEmisor;
import com.cfm.sacc.operaciones.model.ConceptoPago;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.MetodoPago;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.Tarifa;
import com.cfm.sacc.operaciones.model.TipoHonorario;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OperacionesServices implements IOperacionesServices {
	
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired 
	IClientWsService clientWsService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${operaciones.catalogo.conceptos.pago.url}")
	String urlConceptosPago;
	
	@Value("${operaciones.catalogo.tipos.honorarios.url}")
	String urlTiposHonorarios;
	
	@Value("${operaciones.catalogo.tarfias.url}")
	String urlTarifas;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConceptoPago> getConceptosPago() {
		List<ConceptoPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlConceptosPago, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<ConceptoPago>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoHonorario> getTiposHonorarios() {
		List<TipoHonorario> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTiposHonorarios, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<TipoHonorario>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tarifa> getTarifas() {
		List<Tarifa> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTarifas, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Tarifa>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormaPago> getFormasPago() {
		List<FormaPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlFormasPago, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<FormaPago>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BancoEmisor> getBancosEmisor() {
		List<BancoEmisor> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlBancosEmisor, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<BancoEmisor>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetodoPago> getMetodosPago() {
		List<MetodoPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlMetodosPago, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<MetodoPago>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Periodo> getPeridosByCliente(String clienteRFC) {
		String url = urlPeriodos.concat(clienteRFC);
		List<Periodo> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Periodo>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus addPago(Pago pago) {
		ResponseEntity<JsonNode> response;
		response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlAddPago, pago, HttpMethod.POST, APPLICATION_JSON);
		return response.getStatusCode();
	}
}
