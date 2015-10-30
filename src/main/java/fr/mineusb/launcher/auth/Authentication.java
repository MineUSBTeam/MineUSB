package fr.mineusb.launcher.auth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Authentication {
	
	public static final String MOJANG_URL = "https://authserver.mojang.com/";
	
	private String authURL;
	
	public Authentication(String authURL) {
		this.authURL = authURL;
	}
	
	public AuthRep authenticate(String username, String password, String clientToken) throws IOException {
		AuthUserInfos a = new AuthUserInfos(username, password, clientToken);
		return (AuthRep)sendRequest(a, "authenticate", AuthRep.class);
	}
	
	public Object sendRequest(Object r, String points, Class<?> model) throws IOException {
		Gson g = new Gson();
		System.out.println("json: " + g.toJson(r));
		String rep = sendPostRequest(this.authURL + points, g.toJson(r));
		return g.fromJson(rep, model);
	}
	
	public String sendPostRequest(String auth, String json) throws IOException {
		URL servURL = new URL(auth);
		HttpURLConnection c = (HttpURLConnection)servURL.openConnection();
		c.setRequestMethod("POST");
		c.setDoOutput(true);
		c.addRequestProperty("Content-Type", "application/json");
		DataOutputStream wr = new DataOutputStream(c.getOutputStream());
		wr.writeBytes(json);
		wr.flush();
		wr.close();
		c.connect();
		int repCode = c.getResponseCode();
		if(repCode == 204) {
			c.disconnect();
			return null;
		}
		InputStream is = null;
		is = c.getInputStream();
		String rep = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		rep = br.readLine();
		try {
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		c.disconnect();
		return rep;
	}
}
