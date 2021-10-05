package com.cfm.sacc.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class Parseador {
	
	private Parseador() {	    
	  }
	
	public static java.lang.String objectToJson(java.lang.String uid, Object contenedor) {
		
		final ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		java.lang.String respuestaJson = null;

		try {
			respuestaJson = objectMapper.writeValueAsString(contenedor);
		} catch (java.lang.Exception exception) {			
			LogHandler.error(uid, Parseador.class, "Error en jsonToObject >> ", exception);
		}

		return respuestaJson;
	}	
}
