package org.mridul.ClientPoll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class APICalls {
	
	URIBuilder uriBuilder = new URIBuilder().setScheme("http")
						    .setHost("localhost:8080");
	HttpClient client = HttpClientBuilder.create().build();				    
	
	public String getInfo(int requestId) throws IOException, URISyntaxException {
		URI uri = uriBuilder.setPath("/sdhacks/api/getInfo/"+requestId).build();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = client.execute(httpget);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}

		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(result.toString(), HashMap.class);
		return (String) jsonMap.get("Info");
	}
	
	public String getStatus(int requestId) throws IOException, URISyntaxException {
		URI uri = uriBuilder.setPath("/sdhacks/api/getStatus/"+requestId).build();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = client.execute(httpget);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}

		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(result.toString(), HashMap.class);
		return (String) jsonMap.get("Status");
	}
	
	public Map<String, Object> poll() throws ClientProtocolException, IOException, URISyntaxException {
		URI uri = uriBuilder.setPath("/sdhacks/api/poll").build();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = client.execute(httpget);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}

		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(result.toString(), HashMap.class);
		return jsonMap;
	}
	
	public Map<String, Object> sendResponseTime(int userId, int time) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = uriBuilder.setPath("/sdhacks/api/response-time/"+userId+"/"+time).build();
		HttpPut httpput = new HttpPut(uri);
		HttpResponse response = client.execute(httpput);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}

		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(result.toString(), HashMap.class);
		return jsonMap;
	}
	
	public Map<String, Object> downloadComplete(int userId) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = uriBuilder.setPath("/sdhacks/api/done/"+userId).build();
		HttpPut httpput = new HttpPut(uri);
		HttpResponse response = client.execute(httpput);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}

		HashMap<String,Object> jsonMap = new ObjectMapper().readValue(result.toString(), HashMap.class);
		return jsonMap;
	}
	
	
}