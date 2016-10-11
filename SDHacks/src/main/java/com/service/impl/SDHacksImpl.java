package com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.response.InfoResponse;
import com.response.PollResponse;
import com.response.RequestResponse;
import com.response.ResponseTimeResponse;
import com.response.StatusResponse;
import com.service.SDHacks;
import com.service.helpers.Request;

public class SDHacksImpl implements SDHacks {

	private ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();
	private int info = -1;
	private Map<String, ArrayList<Integer>> infoMap = new HashMap<>();
	private Integer totalRequestsServed = 0;
	// private Map<String, Integer> ipPortToGroupIdMap = new HashMap<>();
	// private Map<Integer, HashMap<Integer, ArrayList<Integer>>>
	// requestDistribution = new HashMap<>();

	private int pollCount = 0;
	private int maxUsers = 3;
	private double totalBandwidth = 0;
	private boolean functionCalled = false;
	private boolean fileChunked = false;
	Set<Integer> usersDone = new HashSet<Integer>();

	private Map<Integer, Double> bandwidth = new HashMap<>();
	private Map<Integer, ArrayList<Integer>> chunkForUsers = new HashMap<>();

	private void chunkFile() {
		Integer size = requests.peek().getSize();
		int partition = 0;
		int countUsers = 0;
		for (Map.Entry<Integer, Double> entry : bandwidth.entrySet()) {
			countUsers++;

			ArrayList<Integer> currChunk = new ArrayList<>();
			currChunk.add(partition);
			if (maxUsers == countUsers) {
				currChunk.add(size);
				currChunk.add(countUsers-1);
			} else {
				partition += size
						* ((double) entry.getValue() / totalBandwidth);
				currChunk.add(partition);
				currChunk.add(countUsers-1);
				partition++;
			}

			chunkForUsers.put(entry.getKey(), currChunk);
		}
		fileChunked = true;
	}

	@Override
	public Response initRequest(Integer userId, String link, Integer size) {
		if (userId < maxUsers) {
			requests.add(new Request(userId, link, size, totalRequestsServed));

			RequestResponse initResponse = new RequestResponse("ok",
					totalRequestsServed);
			totalRequestsServed++;
			return Response.ok(initResponse, MediaType.APPLICATION_JSON)
					.build();
		} else {
			return Response.ok(
					new StatusResponse("userId should be less than : "
							+ maxUsers), MediaType.APPLICATION_JSON).build();
		}
	}

	@Override
	public Response getStatus(Integer id) {
		if (info == id) {
			StatusResponse statusResponse = new StatusResponse(
					"This is the current request");
			return Response.ok(statusResponse, MediaType.APPLICATION_JSON)
					.build();
		} else if (!requests.isEmpty()
				&& requests.peek().getNumRequest().intValue() < id.intValue()) {
			StatusResponse statusResponse = new StatusResponse(
					"Request has been processed");
			return Response.ok(statusResponse, MediaType.APPLICATION_JSON)
					.build();
		} else if (!requests.isEmpty()
				&& requests.peek().getNumRequest().intValue() > totalRequestsServed
						.intValue()) {
			StatusResponse statusResponse = new StatusResponse(
					"No such request");
			return Response.ok(statusResponse, MediaType.APPLICATION_JSON)
					.build();
		} else if (!requests.isEmpty()
				&& requests.peek().getNumRequest().intValue() > id.intValue()) {
			StatusResponse statusResponse = new StatusResponse(
					"Request pending");
			return Response.ok(statusResponse, MediaType.APPLICATION_JSON)
					.build();
		}
		StatusResponse statusResponse = new StatusResponse("Invalid call");
		return Response.ok(statusResponse, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response getInfo(Integer id) {
		if (info == id) {
			InfoResponse infoResponse = new InfoResponse(infoMap);
			return Response.ok(infoResponse, MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(new StatusResponse(
						"Another request is being processed. No info available"),
						MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response userIsDone(Integer userId) {
		System.out.println("******* " + userId + "is done ******");
		usersDone.add(userId);
		if (maxUsers == usersDone.size()) {
			pollCount = 0;
			totalBandwidth = 0;
			functionCalled = false;
			fileChunked = false;
			usersDone.clear();
			bandwidth.clear();
			chunkForUsers.clear();
			info = -1;
		}
		return Response.ok(new StatusResponse("true"),
				MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response setResponseTime(Integer userId, Integer responseTime) {
		double currBandwidth = (double) 1 / responseTime;
		bandwidth.put(userId, currBandwidth);
		totalBandwidth += currBandwidth;

		while (true) {
			try {
				if (bandwidth.size() == maxUsers) {
					if (!functionCalled) {
						functionCalled = true;
						chunkFile();
					}
					if (fileChunked) {
						break;
					}
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("exception while sleeping the thread");
				e.printStackTrace();
			}
		}

		return Response.ok(
				new ResponseTimeResponse(chunkForUsers.get(userId).get(0),
						chunkForUsers.get(userId).get(1), chunkForUsers.get(userId).get(2)),
				MediaType.APPLICATION_JSON).build();
	}

	// @Override
	// public Response createGroup(String ip, Integer port) {
	// CreateGroupResponse createGroupResponse;
	// if(ipPortToGroupIdMap.containsKey(ip+port.toString())) {
	// createGroupResponse = new CreateGroupResponse("false",-1);
	// }
	// else {
	// maxGroupId++;
	// ipPortToGroupIdMap.put(ip+port.toString(),maxGroupId);
	// createGroupResponse = new CreateGroupResponse("true",maxGroupId);
	// }
	// return Response.ok(createGroupResponse,
	// MediaType.APPLICATION_JSON).build();
	// }
	// @Override
	// public Response joinGroup(String ip, Integer port, Integer groupId) {
	// CreateGroupResponse createGroupResponse;
	// if(ipPortToGroupIdMap.containsKey(ip+port.toString())) {
	// createGroupResponse = new CreateGroupResponse("false",-1);
	// }
	// else {
	// ipPortToGroupIdMap.put(ip+port.toString(),groupId);
	// createGroupResponse = new CreateGroupResponse("true",groupId);
	// }
	// return Response.ok(createGroupResponse,
	// MediaType.APPLICATION_JSON).build();
	// }
	@Override
	public Response poll() {
		PollResponse pollResponse;
		if (info == -1 && !requests.isEmpty() || (pollCount>=maxUsers)) {
			pollCount++;
			pollResponse = new PollResponse("true", requests.peek().getLink(),
					0, 200, pollCount, requests.peek().getUserId(), requests
							.peek().getNumRequest());

			if (pollCount == maxUsers) {
				info = requests.peek().getNumRequest();
			}
		} else {
			pollResponse = new PollResponse("false");
		}

		return Response.ok(pollResponse, MediaType.APPLICATION_JSON).build();
	}

}
