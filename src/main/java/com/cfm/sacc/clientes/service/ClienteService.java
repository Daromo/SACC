package com.cfm.sacc.clientes.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.util.Parseador;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${clientes.activos.url}")
	String urlClientesActivos;
	
	@Value("${clientes.buscar.rfc.url}")
	String urlBuscarCliente;
	
	@Override
	public List<Cliente> getClientes() {
		List<Cliente> flagList;
		try {
			JsonNode response = (JsonNode) clientWsService.consumeService(urlClientesActivos, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response);
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}

	@Override
	public List<Cliente> findClienteByRFC(String clienteRFC) {
		String url = urlBuscarCliente.concat("/").concat(clienteRFC);
		List<Cliente> flagList;
		try {
			JsonNode response = (JsonNode) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response);
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}
}
