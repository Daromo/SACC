package com.cfm.sacc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cfm.sacc.model.Cliente;
import com.cfm.sacc.ws.client.IClientWsService;

public class ClienteService implements IClienteService{

	private static final String OBJECT_MESSAGE_BODYRESPONSE = "bodyResponse";
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired
	IClientWsService clientWsService;
	
	@Override
	public List<Cliente> getClientes() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type","client_credentials");
		//ResponseEntity<List<Cliente>> myBean = clientWsService.consumeService("http://localhost:8080/clientes/activos",map,HttpMethod.POST,APPLICATION_JSON, null);
		return null;
	}
}
