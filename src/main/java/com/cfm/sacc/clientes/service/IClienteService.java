package com.cfm.sacc.clientes.service;

import java.util.List;

import com.cfm.sacc.clientes.model.Cliente;

public interface IClienteService {
	List<Cliente> getClientes();
	List<Cliente> findClienteByRFC(String clienteRFC);
}
