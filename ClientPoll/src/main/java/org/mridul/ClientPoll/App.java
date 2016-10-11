package org.mridul.ClientPoll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException,
			ClientProtocolException, IOException, URISyntaxException {
		Map<Integer, HashMap<Integer, HashMap<String, Integer>>> filesInfo = new HashMap<Integer, HashMap<Integer, HashMap<String, Integer>>>();
		// userId -> reqId -> File -> numpartition
		APICalls apiCalls = new APICalls();
		Scanner reader = new Scanner(System.in);
		try {

			int userId = reader.nextInt();
			reader.nextLine();

			while (true) {
				Map<String, Object> responsePoll = apiCalls.poll();
				if (((String) responsePoll.get("success")).equals("true")) {
					int lb = (Integer) responsePoll.get("lowerBound");
					int rb = (Integer) responsePoll.get("upperBound");
					String link = (String) responsePoll.get("link");
					int reqId = (Integer) responsePoll.get("RequestID");
					int srcUserId = (Integer) responsePoll.get("sourceUserId");
					Runtime r = Runtime.getRuntime();
					Process p = r.exec("curl -r " + lb + "-" + rb
							+ " -o /dev/null -s -w %{time_total}\\n " + " '"
							+ link + "'");
					p.waitFor();
					BufferedReader b = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line = "";

					line = b.readLine();
					b.close();
					Double rt = Double.parseDouble(line) * 1000;
					int respTime = rt.intValue();
					Map<String, Object> response = apiCalls.sendResponseTime(
							userId, respTime);
					lb = (Integer) response.get("lowerBound");
					rb = (Integer) response.get("upperBound");
					int numPartition = (Integer) response.get("numPartition");

					p = r.exec(new String[]{"bash","-c","curl -r " + lb + "-" + rb + " -o " + reqId
							+ "_" + userId + "_" + numPartition + " '" + link
							+ "'"});
					p.waitFor();
					
					System.out.println(apiCalls.downloadComplete(userId));
					
					if (filesInfo.containsKey(srcUserId)) {
						if (filesInfo.get(srcUserId).containsKey(reqId)) {
							filesInfo
									.get(srcUserId)
									.get(reqId)
									.put(System.getProperty("user.dir") + "/"
											+ reqId + "_" + userId + "_"
											+ numPartition, numPartition);
						}
						else {
							HashMap<String, Integer> fileLoc = new HashMap<String, Integer>();
							fileLoc.put(System.getProperty("user.dir") + "/"
											+ reqId + "_" + userId + "_"
											+ numPartition, numPartition);
							filesInfo.get(srcUserId).put(reqId, fileLoc);
						}
					} else {
						HashMap<String, Integer> fileLoc = new HashMap<String, Integer>();
						fileLoc.put(System.getProperty("user.dir") + "/"
										+ reqId + "_" + userId + "_"
										+ numPartition, numPartition);
						
						HashMap<Integer, HashMap<String, Integer>> reqInfo = new HashMap<Integer, HashMap<String, Integer>>();
						reqInfo.put(reqId, fileLoc);
						filesInfo.put(srcUserId, reqInfo);
					}

				}
				Thread.sleep(200);
			}
		} finally {
			reader.close();
		}
	}
}
