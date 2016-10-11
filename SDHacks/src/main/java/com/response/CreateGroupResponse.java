package com.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGroupResponse {
	String success;
	Integer groupId;
	
	public CreateGroupResponse(String success, Integer groupId) {
		this.success = success;
		this.groupId = groupId;
	}
	
	@JsonProperty("success") 
	public String getSuccess() {
		return success;
	}
	
	@JsonProperty("group ID")
	public Integer getGroupId() {
		return groupId;
	}
}
