package com.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {
	String status;
	Integer id;
	public RequestResponse(String s, Integer id) {
		status = s;
		this.id = id;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	
	@JsonProperty("ID")
	public Integer getId() {
		return id;
	}
}
