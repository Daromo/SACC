package com.cfm.sacc.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteService implements IClienteService{

	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired
	IClientWsService clientWsService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${clientes.activos.url}")
	String urlClientesActivos;
	
	@Override
	public List<Cliente> getClientes() {
		try {
			JsonNode response = (JsonNode) clientWsService.consumeService(urlClientesActivos, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response);
			System.out.println("JSON NODE: " + json);
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			// AGREGAR LOG
			e.printStackTrace();
		}
		// retornan lista vacia
		return null;
	}
}
