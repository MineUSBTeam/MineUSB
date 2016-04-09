package fr.mineusb.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.mineusb.MineUSB;
import java.io.*;

public class JsonConfig
{
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private File file;
	private JsonObject json;

	public JsonConfig(File file) {
		this.file = file;
		reloadConfiguration();
	}

	public String getLauncherType()
	{
		return this.json.get("used_launcher").getAsString();
	}

	public String getLang()
	{
		return this.json.get("lang").getAsString();
	}

	public String getUsername() {
        return this.json.getAsJsonObject("auth").get("username").getAsString();
    }

    public String getPassword() {
        return this.json.getAsJsonObject("auth").get("password").getAsString();
    }

	public void setLauncherType(String launcher)
	{
		try
		{
			FileWriter configWriter = new FileWriter(file);
			json.remove("used_launcher");
            json.addProperty("used_launcher", launcher);
            configWriter.append(gson.toJson(json));
			configWriter.close();

			reloadConfiguration();
		}
		catch (IOException e)
		{

			MineUSB.getConsole().error(e);
			System.exit(1);

		}
	}

	public void setLang(String lang)
	{
		try
		{
			FileWriter configWriter = new FileWriter(file);
            json.remove("lang");
            json.addProperty("lang", lang);
			configWriter.append(gson.toJson(json));
			configWriter.close();

			reloadConfiguration();
		}
		catch (IOException e)
		{

			MineUSB.getConsole().error(e);
			System.exit(1);

		}
	}

	public void setUserAndPWD(String username, String pwd) {
        try
        {
            FileWriter configWriter = new FileWriter(file);
            json.remove("auth");
            JsonObject auth = new JsonObject();
            auth.addProperty("username", username);
            auth.addProperty("password", pwd);
            json.add("auth", auth);
            configWriter.append(gson.toJson(json));
            configWriter.close();

            reloadConfiguration();
        }
        catch (IOException e)
        {
            MineUSB.getConsole().error(e);
            System.exit(1);
        }
    }

	@SuppressWarnings("deprecation")
	public void reloadConfiguration()
	{
		if (!file.exists())
		{
			if (file.getName().equals("config.json"))
			{
				String launcherValue = "Minecraft";
				String langValue = "ENGLISH";
				File oldFile = new File(MineUSB.getMineUSBFolder(),
						"config.properties");
				if (oldFile.exists())
				{
					MineUSBConfig oldConfig = new MineUSBConfig(oldFile);
					launcherValue = oldConfig.getLauncherType();
					langValue = oldConfig.getLang();
				}
				try
				{
					FileWriter configWriter = new FileWriter(file);
					JsonObject json = new JsonObject();
                    json.addProperty("used_launcher", launcherValue);
                    json.addProperty("lang", langValue);
                    JsonObject auth = new JsonObject();
                    auth.addProperty("username", "404@null.com");
                    auth.addProperty("password", "non-encoded-response");
                    json.add("auth", auth);
                    configWriter.append(gson.toJson(json));
                    configWriter.close();
				}
				catch (IOException e)
				{
					MineUSB.getConsole().error(e);
					System.exit(1);
				}

			}
		}
		FileReader reader;
		try
		{
			reader = new FileReader(file);
			Object obj = new JsonParser().parse(reader);
			reader.close();
			if (!(obj instanceof JsonObject))
			{
				this.file.delete();
				reloadConfiguration();
				return;
			}
			this.json = (JsonObject) obj;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
