package fr.mineusb.system;

import fr.mineusb.MineUSB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

@Deprecated
public class MineUSBConfig {

	private static File file;
	public static final Properties properties = new Properties();
	private static Logger console;

	public MineUSBConfig(File configFile) {
		file = configFile;
		console = MineUSB.getConsole();
		reloadConfiguration();
	}

	public void saveMineUSBConfigFile() {
		FileOutputStream fileoutputstream = null;
		try {
			if ((file.exists()) && (!file.canWrite())) {
				return;
			}
			fileoutputstream = new FileOutputStream(file);
			properties.store(fileoutputstream, "MineUSB properties");
			return;
		} catch (Exception exception) {
			console.error("Failed to save config.properties");
			console.error(exception.getMessage());
		} finally {
			if (fileoutputstream != null) {
				try {
					fileoutputstream.close();
				} catch (IOException ioexception) {
				}
			}
		}
	}

	public Object get(String path, Object def) {
		return properties.getProperty(path, def.toString());
	}

	public String getString(String path, String def) {
		return properties.getProperty(path, def);
	}

	public boolean getBoolean(String path, boolean def) {
		return Boolean
				.valueOf(properties.getProperty(path, String.valueOf(def)));
	}

	public int getInt(String path, int def) {
		return Integer
				.valueOf(properties.getProperty(path, String.valueOf(def)));
	}

	public String getLauncherType() {
		return properties.getProperty("UsedLauncher", "Minecraft");
	}

	public String getLang() {
		return properties.getProperty("Lang", "ENGLISH");
	}

	public void setString(String path, String value) {
		properties.put(path, value);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out, "MineUSB properties");
			out.close();
		} catch (IOException e) {
			console.error("Unable to write config file.");
		}
	}

	public void setBoolean(String path, boolean value) {
		properties.put(path, value);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out, "MineUSB properties");
			out.close();
		} catch (IOException e) {
			console.error("Unable to write config file.");
		}
	}

	public void setInt(String path, int value) {
		properties.put(path, value);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out, "MineUSB properties");
			out.close();
		} catch (IOException e) {
			console.error("Unable to write config file.");
		}
	}

	public void setLauncherType(String value) {
		properties.put("UsedLauncher", value);
		properties.put("Lang", this.getLang());
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out, "MineUSB properties");
			out.close();
		} catch (IOException e) {
			console.error("Unable to write config file.");
		}
	}

	public void setLang(String value) {
		properties.put("UsedLauncher", this.getLauncherType());
		properties.put("Lang", value);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out, "MineUSB properties");
			out.close();
		} catch (IOException e) {
			console.error("Unable to write config file.");
		}
	}

	public static void reloadConfiguration() {
		if (!file.exists()) {
			if (file.getName().equals("config.properties")) {
				properties.put("UsedLauncher", "Minecraft");
				properties.put("Lang", "ENGLISH");
				FileOutputStream out;
				try {
					out = new FileOutputStream(file);
					properties.store(out, "MineUSB properties");
					out.close();
				} catch (IOException e) {
					console.error("Failed.");
					console.error("Unable to write config file.");
				}
			}
		}
		try {
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			console.error("Unable to load config file.");
		}
	}
}
