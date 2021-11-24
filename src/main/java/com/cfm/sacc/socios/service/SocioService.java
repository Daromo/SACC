package com.cfm.sacc.socios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.socios.model.Socio;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.ws.client.IClientWsService;
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
	
	@Value("${socios.activos.lista.url}")
	String urlSociosActivos;
	
	@Value("${socios.lista.url}")
	String urlAllSocios;
	
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

}
