package com.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoResponse {
	Map<String, ArrayList<Integer>> info = new HashMap<String, ArrayList<Integer>>();
	
	public InfoResponse(Map<String, ArrayList<Integer>> info) {
		this.info = info;
	}
	
	@JsonProperty("Info")
	public Map<String, ArrayList<Integer>> getInfo() {
		return info;
	}
}
