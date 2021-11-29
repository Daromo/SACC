package com.cfm.sacc.socios.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.socios.model.Porcentaje;
import com.cfm.sacc.socios.model.PorcentajeSocioRep;
import com.cfm.sacc.socios.model.Socio;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SocioService implements ISocioService {
	
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired 
	IClientWsService clientWsService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ModelMapper modelMapper;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Socio> getAllSocios() {
		List<Socio> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlAllSocios, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Socio>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "getListadoSocios", e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Socio> getSociosActivos() {
		List<Socio> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlSociosActivos, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Socio>>(){});
		}catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "getSociosActivos", e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PorcentajeSocioRep> getAllPorcentajes() {
		List<PorcentajeSocioRep> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlAllPorcentajes, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<PorcentajeSocioRep>>(){});
		}catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "getAllPorcentajes", e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus addPorcentaje(List<Porcentaje> listaPorcentajes) throws JsonProcessingException {
		ResponseEntity<JsonNode> responseLastPorcentaje = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlLastClavePorcentaje, null, HttpMethod.GET, "text/plain, java.lang.String");
		String contentBody = objectMapper.writeValueAsString(responseLastPorcentaje.getBody());
		JsonNode json = objectMapper.readTree(contentBody);
		String clave = json.get("clave").toString();
		Integer claveNum = Integer.parseInt(clave.substring(4, clave.length()-1));
		
		listaPorcentajes.forEach(porcentaje -> porcentaje.setClave("PS-"+(claveNum+1)));
		
		ResponseEntity<JsonNode> responseSave = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlAgregarPorcentaje, listaPorcentajes, HttpMethod.POST, APPLICATION_JSON);
		return responseSave.getStatusCode();
	}

}
