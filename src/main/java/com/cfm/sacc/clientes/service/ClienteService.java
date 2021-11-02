package com.cfm.sacc.clientes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.model.RegimenFiscal;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	@Value("${clientes.inactivos.url}")
	String urlClientesInactivos;
	
	@Value("${clientes.buscar.rfc.url}")
	String urlBuscarCliente;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> getClientesActivos(){
		List<Cliente> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlClientesActivos, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> getClientesInactivos() {
		List<Cliente> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlClientesInactivos, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cliente findByRFC(String clienteRFC) {
		String url = urlBuscarCliente.concat(clienteRFC);
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, Cliente.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus bajaCliente(String clienteRFC) {
		String url = urlBajaCliente.concat(clienteRFC);
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.PUT, APPLICATION_JSON);		
		return response.getStatusCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus reactivarCliente(String clienteRFC) {
		String url = urlReactivarCliente.concat(clienteRFC);
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.PUT, APPLICATION_JSON); 
		return response.getStatusCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegimenFiscal> getRegimenFiscal(){
		List<RegimenFiscal> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlListaRegimenFiscales, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<RegimenFiscal>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getRegimenFiscal"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		} catch (ResourceAccessException e) {
			flagList = new ArrayList<>();
		}
		
		return flagList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus addCliente(Cliente cliente) {
		ResponseEntity<JsonNode> response;
		if (cliente.getFechaIngreso() == null) {
			response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlAgregarCliente, cliente, HttpMethod.POST, APPLICATION_JSON);
		}else
			response = (ResponseEntity<JsonNode>) clientWsService.consumeService(urlModificarCliente, cliente, HttpMethod.PUT, APPLICATION_JSON);
		return response.getStatusCode();
	}	
}
