package org.mridul.ClientRequest;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestDownload {
	URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(
			"localhost:8080");
	HttpClient client = HttpClientBuilder.create().build();

	public Map<String, Object> requestDownload(int userId, String link, int size)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = uriBuilder.setPath("/sdhacks/api/download/" + userId)
				.setParameter("link", link)
				.setParameter("size", Integer.toString(size)).build();
		HttpPost httppost = new HttpPost(uri);
		HttpResponse response = client.execute(httppost);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		HashMap<String, Object> jsonMap = new ObjectMapper().readValue(
				result.toString(), HashMap.class);
		return jsonMap;
	}
}
