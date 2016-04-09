package fr.mineusb.system.launcher.auth;

import com.google.gson.*;
import fr.mineusb.system.launcher.auth.AuthProfile.AuthProfileSerializer;
import java.lang.reflect.Type;

/**
 * Created by w67clement on 08/04/2016.
 * <p>
 * Class of project: MineUSB
 */
public class AuthResponse
{
    private String accessToken;
    private String clientToken;
    private AuthProfile selectedProfile;

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getClientToken()
    {
        return clientToken;
    }

    public AuthProfile getSelectedProfile()
    {
        return selectedProfile;
    }

    public static class AuthResponseSerializer implements JsonDeserializer<AuthResponse>, JsonSerializer<AuthResponse>
    {
        private static final AuthProfileSerializer authProfileSerializer = new AuthProfileSerializer();

        @Override
        public AuthResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            AuthResponse response = new AuthResponse();
            if (json instanceof JsonObject) {
                JsonObject jsonObj = (JsonObject) json;
                response.accessToken = jsonObj.get("accessToken").getAsString();
                response.clientToken = jsonObj.get("clientToken").getAsString();
                response.selectedProfile = authProfileSerializer.deserialize(jsonObj.getAsJsonObject("selectedProfile"), typeOfT, context);
            }
            return response;
        }

        @Override
        public JsonElement serialize(AuthResponse src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();
            json.addProperty("accessToken", src.accessToken);
            json.addProperty("clientToken", src.clientToken);
            json.add("selectedProfile", authProfileSerializer.serialize(src.selectedProfile, typeOfSrc, context));
            return json;
        }
    }
}
