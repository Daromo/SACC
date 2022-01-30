package com.cfm.sacc.clientes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.model.RegimenFiscal;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IClienteService {
	List<Cliente> getClientesActivos();
	List<Cliente> getClientesInactivos();
	List<RegimenFiscal> getRegimenFiscal();
	Cliente findByRFC(String clienteRFC);
	HttpStatus bajaCliente(String clienteRFC);
	HttpStatus reactivarCliente(String clienteRFC);
	ResponseEntity<String> guardarCliente(Cliente cliente) throws JsonProcessingException;
	List<Cliente> searchCliente(Cliente cliente);
}
