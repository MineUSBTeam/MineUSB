package fr.mineusb.system.launcher.auth;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Created by w67clement on 08/04/2016.
 * <p>
 * Class of project: MineUSB
 */
public class AuthProfile
{

    private String id;
    private String name;

    private AuthProfile() {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static class AuthProfileSerializer implements JsonDeserializer<AuthProfile>, JsonSerializer<AuthProfile>
    {

        @Override
        public AuthProfile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            AuthProfile authProfile = new AuthProfile();
            if (json instanceof JsonObject) {
                JsonObject jsonObj = (JsonObject) json;
                authProfile.setId(jsonObj.get("id").getAsString());
                authProfile.setName(jsonObj.get("name").getAsString());
            }
            return authProfile;
        }

        @Override
        public JsonElement serialize(AuthProfile src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();
            json.addProperty("id", src.id);
            json.addProperty("name", src.name);
            return json;
        }
    }
}
