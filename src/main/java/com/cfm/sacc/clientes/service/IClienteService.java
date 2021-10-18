package com.cfm.sacc.clientes.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.cfm.sacc.clientes.model.Cliente;
import com.cfm.sacc.clientes.model.RegimenFiscal;
import com.cfm.sacc.exception.BusinessException;

public interface IClienteService {
	List<Cliente> getClientesActivos();
	List<Cliente> getClientesInactivos();
	List<RegimenFiscal> getRegimenFiscal() throws BusinessException;
	Cliente findByRFC(String clienteRFC);
	HttpStatus bajaCliente(String clienteRFC);
	HttpStatus reactivarCliente(String clienteRFC);
}
