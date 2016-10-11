package com.service.helpers;

public class Request {
	private String link;
	private Integer size;
	private Integer numRequest;
	private Integer userId;

	public Request(Integer userId, String link, Integer size, Integer numRequest) {
		this.setUserId(userId);
		this.link = link;
		this.size = size;
		this.setNumRequest(numRequest);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getNumRequest() {
		return numRequest;
	}

	public void setNumRequest(Integer numRequest) {
		this.numRequest = numRequest;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

