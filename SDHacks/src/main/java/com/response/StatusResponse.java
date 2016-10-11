package com.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusResponse {
	String status;

	public StatusResponse(String m) {
		status = m;
	}

	@JsonProperty("Status")
	public String getStatus() {
		return status;
	}
}
