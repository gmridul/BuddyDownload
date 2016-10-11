package com.service.helpers;

public class DistributedRequest {
	private Integer reqId;
	private String ip;
	private Integer lowerBound, upperBound;

	public DistributedRequest(String ip, Integer lb, Integer ub) {
		this.ip = ip;
		this.lowerBound = lb;
		this.upperBound = ub;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

	public Integer getReqId() {
		return reqId;
	}

	public void setReqId(Integer reqId) {
		this.reqId = reqId;
	}

}

