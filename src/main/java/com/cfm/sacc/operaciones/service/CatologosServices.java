package com.cfm.sacc.operaciones.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.cfm.sacc.operaciones.model.BancoEmisor;
import com.cfm.sacc.operaciones.model.ConceptoPago;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.MetodoPago;
import com.cfm.sacc.operaciones.model.Tarifa;
import com.cfm.sacc.operaciones.model.TipoHonorario;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CatologosServices implements ICatalogosServices {
	
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
	String formasPago;
	
	@Value("${operaciones.catalogo.bancos.emisor.url}")
	String bancosEmisor;
	
	@Value("${operaciones.catalogo.metodo.pago.url}")
	String metodosPago;
	
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTiposHonorarios, null, HttpMethod.GET, APPLICATION_JSON);
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTiposHonorarios, null, HttpMethod.GET, APPLICATION_JSON);
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTiposHonorarios, null, HttpMethod.GET, APPLICATION_JSON);
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlTiposHonorarios, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<MetodoPago>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

}
