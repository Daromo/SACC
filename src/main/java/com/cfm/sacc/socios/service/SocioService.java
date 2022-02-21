package com.cfm.sacc.socios.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.config.SociosProperties;
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
	SociosProperties sociosProperties;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ModelMapper modelMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Socio> getAllSocios() {
		List<Socio> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					sociosProperties.getUrlAllSocios(), null, HttpMethod.GET, APPLICATION_JSON);
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					sociosProperties.getUrlSociosActivos(), null, HttpMethod.GET, APPLICATION_JSON);
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
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					sociosProperties.getUrlAllPorcentajes(), null, HttpMethod.GET, APPLICATION_JSON);
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
		ResponseEntity<JsonNode> responseLastPorcentaje = (ResponseEntity<JsonNode>) clientWsService.consumeService(
				sociosProperties.getUrlLastClavePorcentaje(), null, HttpMethod.GET, "text/plain, java.lang.String");
		String contentBody = objectMapper.writeValueAsString(responseLastPorcentaje.getBody());
		JsonNode json = objectMapper.readTree(contentBody);
		String clave = json.get("clave").toString();
		Integer claveNum = Integer.parseInt(clave.substring(4, clave.length()-1));
		
		listaPorcentajes.forEach(porcentaje -> porcentaje.setClave("PS-"+(claveNum+1)));
		
		ResponseEntity<JsonNode> responseSave = (ResponseEntity<JsonNode>) clientWsService.consumeService(
				sociosProperties.getUrlAgregarPorcentaje(), listaPorcentajes, HttpMethod.POST, APPLICATION_JSON);
		return responseSave.getStatusCode();
	}

}
