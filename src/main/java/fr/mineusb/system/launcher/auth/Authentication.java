package fr.mineusb.system.launcher.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.mineusb.system.launcher.MineLauncher.AuthResponseAdvanced;
import fr.mineusb.system.launcher.auth.AuthProfile.AuthProfileSerializer;
import fr.mineusb.system.launcher.auth.AuthResponse.AuthResponseSerializer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by w67clement on 08/04/2016.
 * <p>
 * Class of project: MineUSB
 */
public class Authentication
{
    private static final Authentication instance = new Authentication();
    private final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(AuthResponse.class, new AuthResponseSerializer()).registerTypeHierarchyAdapter(AuthProfile.class, new AuthProfileSerializer()).create();
    private final JsonParser parser = new JsonParser();

    public static Authentication getInstance()
    {
        return instance;
    }

    public AuthResponseAdvanced authenticate(String username, String password)
    {
        HttpRequestResponse response = null;
        Exception responseException = null;
        try
        {
            response = httpRequest(new URL("https://authserver.mojang.com/authenticate"), makeJsonRequest(username, password));
        }
        catch (IOException e)
        {
            responseException = e;
        }
        if (response != null)
        {
            JsonObject json = (JsonObject) parser.parse(response.getResponseJson());
            if (json.has("error"))
            {
                return new AuthResponseAdvanced(gson.fromJson(json, AuthResponse.class), json.toString(), true, response.responseCode, null);
            }
            return new AuthResponseAdvanced(gson.fromJson(json, AuthResponse.class), null, true, response.responseCode, null);
        } else {
            return new AuthResponseAdvanced(null, null, false, 403, responseException);
        }
    }

    private String makeJsonRequest(String username, String password)
    {
        JsonObject json = new JsonObject();
        JsonObject agent = new JsonObject();
        agent.addProperty("name", "Minecraft");
        agent.addProperty("version", 1);
        json.add("agent", agent);
        json.addProperty("username", username);
        json.addProperty("password", password);
        return json.toString();
    }

    private HttpRequestResponse httpRequest(URL url, String json) throws IOException
    {
        HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
        c.setRequestMethod("POST");
        c.setRequestProperty("Content-Type", "application/json");
        c.setDoOutput(true);
        c.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(c.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();
        int responseCode = c.getResponseCode();
        BufferedReader bf = new BufferedReader(new InputStreamReader(c.getInputStream()));
        String line = bf.readLine();
        bf.close();
        return new HttpRequestResponse(responseCode, line);
    }

    private class HttpRequestResponse
    {
        private int responseCode;
        private String responseJson;

        public HttpRequestResponse(int responseCode, String responseJson)
        {
            this.responseCode = responseCode;
            this.responseJson = responseJson;
        }

        public int getResponseCode()
        {
            return responseCode;
        }

        public String getResponseJson()
        {
            return responseJson;
        }
    }
}
