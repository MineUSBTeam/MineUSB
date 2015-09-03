package fr.mineusb.system.launchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.mineusb.MineUSB;
import fr.mineusb.exceptions.LauncherNotFoundException;
import fr.mineusb.system.launchers.defaults.DefaultLauncher;
import fr.mineusb.system.launchers.defaults.MagicLauncher;
import fr.mineusb.system.launchers.defaults.NationsGloryLauncher;
import fr.mineusb.system.launchers.defaults.Skyolauncher;

/**
 * A manager of launchers.
 * 
 * @author 67clement
 * 
 */
public class LauncherManager {

	private static HashMap<String, Launcher> launchers = new HashMap<String, Launcher>();

	public LauncherManager() {
		// Register defaults launcher
		DefaultLauncher launcher = new DefaultLauncher();
		NationsGloryLauncher nationsGlory = new NationsGloryLauncher();
		Skyolauncher skyoLauncher = new Skyolauncher();
		MagicLauncher mgLauncher = new MagicLauncher();
		launchers.put(launcher.getName(), launcher);
		launchers.put(nationsGlory.getName(), nationsGlory);
		launchers.put(skyoLauncher.getName(), skyoLauncher);
		launchers.put(mgLauncher.getName(), mgLauncher);
	}

	public static void registerLauncher(Launcher launcher) {
		if (!launchers.containsKey(launcher.getName())) {
			launchers.put(launcher.getName(), launcher);
			MineUSB.getConsole().info(
					"Launcher: " + launcher.getName() + " was registered!");
		}
	}

	public static void unregisterLauncher(Launcher launcher)
			throws LauncherNotFoundException {
		if (launchers.containsKey(launcher.getName())) {
			launchers.remove(launcher.getName());
		} else {
			throw new LauncherNotFoundException("Launcher "
					+ launcher.getName() + " was not found!");
		}
	}

	public static Launcher getLauncher(String name)
			throws LauncherNotFoundException {
		if (launchers.containsKey(name)) {
			return launchers.get(name);
		} else {
			throw new LauncherNotFoundException("Launcher " + name
					+ " was not found!");
		}
	}

	public static List<Launcher> getLauncherList() {
		List<Launcher> launcherList = new ArrayList<Launcher>();
		for (String name : launchers.keySet()) {
			try {
				launcherList.add(getLauncher(name));
			} catch (LauncherNotFoundException e) {
				MineUSB.getConsole()
						.error("Error: LauncherNotFoundException (Class: LauncherManager, Method: getLauncherList)");
				e.printStackTrace();
			}
		}
		return launcherList;
	}
}
