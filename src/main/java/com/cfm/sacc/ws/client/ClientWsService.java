package com.cfm.sacc.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
@Service
public class ClientWsService implements IClientWsService {

	@Override
	public Object consumeService(String url, Object object, HttpMethod typeMethod, String contentType) {

		RestTemplate restTemplate = new RestTemplate();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("User-Agent", "CFM SACC 1.0.0");
		HttpEntity<Object> requestService = new HttpEntity<>(object,headers);
			
		return restTemplate.exchange(url, typeMethod, requestService, JsonNode.class);
	}
}
