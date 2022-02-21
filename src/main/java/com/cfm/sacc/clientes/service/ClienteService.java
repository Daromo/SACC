package com.cfm.sacc.clientes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.model.RegimenFiscal;
import com.cfm.sacc.config.ClientesProperties;
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
	
	@Autowired
	ClientesProperties clientesProperties;
	
	/**
	 * Consumir API para obtener la lista de cliente con status activo
	 * @return List<Cliente>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> getClientesActivos(){
		List<Cliente> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					clientesProperties.getUrlClientesActivos(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener la lista de cliente con status inactivo
	 * @return List<Cliente>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> getClientesInactivos() {
		List<Cliente> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					clientesProperties.getUrlClientesInactivos(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.info(uid, getClass(), "getCliente"+Parseador.objectToJson(uid, e));
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener el registro de un cliente por su RFC
	 * @param cliente RFC
	 * @return Cliente
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Cliente findByRFC(String clienteRFC) {
		String url = clientesProperties.getUrlBuscarClienteRFC().concat(clienteRFC);
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, Cliente.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Consumir API para dar de baja a un cliente
	 * @param cliente RFC
	 * @return Http response status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus bajaCliente(String clienteRFC) {
		String url = clientesProperties.getUrlBajaCliente().concat(clienteRFC);
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.PUT, APPLICATION_JSON);		
		return response.getStatusCode();
	}

	/**
	 * Consumir API para dar de reactivar a un cliente dado de baja
	 * @param cliente RFC
	 * @return Http response status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HttpStatus reactivarCliente(String clienteRFC) {
		String url = clientesProperties.getUrlReactivarCliente().concat(clienteRFC);
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.PUT, APPLICATION_JSON); 
		return response.getStatusCode();
	}
	
	/**
	 * Consumir API para obtener el catalogo de regimen fiscal
	 * @return List<RegimenFiscal>
	 */
	@Cacheable("regimenFiscalCache")
	@SuppressWarnings("unchecked")
	@Override
	public List<RegimenFiscal> getRegimenFiscal(){
		List<RegimenFiscal> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					clientesProperties.getUrlListaRegimenFiscales(), null, HttpMethod.GET, APPLICATION_JSON);
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
	
	/**
	 * Se valida status de un cliente para determinar el metodo (Modificar o Agregar)
	 * @param Cliente
	 * @throws JsonProcessingException 
	 * @throws  
	 */
	@Override
	public ResponseEntity<String> guardarCliente(Cliente cliente) throws JsonProcessingException {
		try {
			/* Validar el status del cliente para determinar el metodo HTTP: 
			 * Por defecto, cuando se agrega a un nuevo cliente el valor del status siempre sera nulo.*/
			if (cliente.getStatus() == null) {
				clientWsService.consumeService(
						clientesProperties.getUrlAgregarCliente(), cliente, HttpMethod.POST, APPLICATION_JSON);
			}else {
				clientWsService.consumeService(
						clientesProperties.getUrlModificarCliente(), cliente, HttpMethod.PUT, APPLICATION_JSON);
			}
		}catch (Exception e) {
			String messageException = e.getMessage();
			int index = messageException.indexOf(':');
			messageException = messageException.substring(index+1, messageException.length());
			JsonNode json = objectMapper.readTree(messageException);
			String detalleException = json.findPath("detalle").asText();
			return new ResponseEntity<>(detalleException, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	/**
	 * Consumir API para buscar cliente de acuerdo a las propiedades del objeto que
	 * se recibe como parametro, en la vista se forza al usuario a buscar solo por
	 * RFC o Regimen Fiscal.
	 * @param Cliente
	 * @return List<Cliente>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> searchCliente(Cliente cliente) {
		List<Cliente> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					clientesProperties.getUrlBuscarCliente(), cliente, HttpMethod.POST, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});
		} catch (JsonProcessingException e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "searchCliente", e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}	
}
