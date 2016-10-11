package org.mridul.ClientRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws MalformedURLException,
			URISyntaxException {
		RequestDownload requestDownload = new RequestDownload();
		Scanner reader = new Scanner(System.in);
		try {
			int userId = reader.nextInt();
			reader.nextLine();
			while (true) {
				System.out.println("Enter URL to download");
				String link = reader.nextLine();
				URL url = new URL(link);
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.getInputStream();
					int size = conn.getContentLength();
					System.out.println(requestDownload.requestDownload(userId,
							link, size));
				} catch (IOException e) {
					return;
				} finally {
					conn.disconnect();
				}
			}
		} finally {
			reader.close();
		}

	}
}
