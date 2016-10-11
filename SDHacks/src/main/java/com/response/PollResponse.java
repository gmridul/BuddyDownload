package com.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PollResponse {
	private String requestReady;
	private String link;
	private Integer lowerBound;
	private Integer upperBound;
	private Integer pollCount;
	private Integer sourceUserId;
	private Integer reqId;
	
	public PollResponse(String s) {
		requestReady = s;
		link = " ";
		lowerBound = -1;
		upperBound = -1;
		pollCount = -1;
		sourceUserId = -1;
		reqId = -1;
	}
	
	public PollResponse(String s, String link, Integer lb, Integer ub, Integer pc, Integer id, Integer reqId) {
		this.requestReady = s;
		this.link = link;
		this.lowerBound = lb;
		this.upperBound = ub;
		this.pollCount = pc;
		this.sourceUserId = id;
		this.reqId = reqId;
	}
	
	@JsonProperty("success")
	public String getRequestReady() {
		return requestReady;
	}
	
	@JsonProperty("link")
	public String getLink() {
		return link;
	}
	
	@JsonProperty("lowerBound")
	public Integer getLowerBound() {
		return lowerBound;
	}
	
	@JsonProperty("upperBound")
	public Integer getUpperBound() {
		return upperBound;
	}
	
	@JsonProperty("pollCount")
	public Integer getPollCount() {
		return pollCount;
	}

	@JsonProperty("sourceUserId")
	public Integer getSourceUserId() {
		return sourceUserId;
	}

	@JsonProperty("RequestID")
	public Integer getReqId() {
		return reqId;
	}
}
