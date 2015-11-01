package fr.mineusb.auth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Auth {
	
	private String username;
	private String password;
	
	public Auth(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean authenticate() throws JSONException, IOException {
		String http = "";
		try {
		http = httpRequest(new URL("https://authserver.mojang.com/authenticate"), MakeJSONRequest(this.username, this.password));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(http);
		return false;
	}
	
	private String MakeJSONRequest(String username, String password) throws JSONException {
		JSONObject json1 = new JSONObject();
		json1.put("name", "Minecraft");
		json1.put("version", 1);
		JSONObject json = new JSONObject();
		json.put("agent", json1);
		json.put("username", username);
		json.put("password", password);
		return json.toString();
	}
	
	private String httpRequest(URL url, String content) throws IOException {
		HttpsURLConnection c = (HttpsURLConnection)url.openConnection();
		c.setRequestMethod("POST");
		c.setRequestProperty("Content-Type", "application/json");
		c.setDoOutput(true);
		c.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(c.getOutputStream());
		wr.writeBytes(content);
		wr.flush();
		wr.close();
		int repCode = c.getResponseCode();
		BufferedReader bf = new BufferedReader(new InputStreamReader(c.getInputStream()));
		String line = "";
		line = bf.readLine();
		bf.close();
		return line;
	}
}
