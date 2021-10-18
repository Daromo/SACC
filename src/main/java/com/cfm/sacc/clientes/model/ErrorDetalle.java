package com.cfm.sacc.clientes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "errors" })
public class ErrorDetalle {

	@JsonProperty("errors")
	private List<ErrorDescripcion> errors = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("errors")
	public List<ErrorDescripcion> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(List<ErrorDescripcion> errors) {
		this.errors = errors;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}
	

}
