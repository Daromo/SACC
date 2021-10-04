package com.cfm.sacc.ws.client;

import org.springframework.http.HttpMethod;

public interface IClientWsService {
	Object consumeService(String url, Object object, HttpMethod typeMethod, String contentType, 
			String authorization, Object typeResponse);
}