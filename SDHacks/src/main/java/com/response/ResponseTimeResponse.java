package com.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTimeResponse {
	private Integer lowerBound, upperBound, numPartition;
	
	public ResponseTimeResponse(Integer lb, Integer ub, Integer numPartition) {
		this.lowerBound = lb;
		this.upperBound = ub;
		this.numPartition = numPartition;
	}
	
	@JsonProperty("lowerBound")
	public Integer getLowerBound() {
		return lowerBound;
	}
	
	@JsonProperty("upperBound")
	public Integer getUpperBound() {
		return upperBound;
	}
	
	@JsonProperty("numPartition")
	public Integer getNumParititon() {
		return numPartition;
	}
	
}
