package com.aol.mineusb;

import java.io.File;

import javax.swing.UIManager;

import com.aol.mineusb.system.launchers.LauncherManager;
import com.aol.mineusb.ui.MineUSBFrame;
import com.aol.mineusb.ui.OptionsFrame;
import com.aol.mineusb.ui.TestFrame;
import com.aol.w67clement.mineapi.enums.OSType;
import com.aol.w67clement.mineapi.logger.MineLogger;
import com.aol.w67clement.mineapi.logger.MineLoggerManager;

public class MineUSB {

	private static final String version = "Beta 1.0.1";
	// Logger
	private static MineLogger console;
	// Frame
	private static MineUSBFrame frame;
	private static OptionsFrame optionsFrame;
	// Computer's OS
	private static OSType os;
	//Files 
	private static File binFolder;
	private static File dataFolder;

	public static void main(String[] args) {
		// Initialize MineUSBLogger
		MineLoggerManager.registerLogger("MineUSB");
		console = MineLoggerManager.getLogger("MineUSB");
		// Starting MineUSB
		console.info("Starting MineUSB " + version);
		os = OSType.getCurrentPlatform();
		console.info("OS used: " + os.getOSName());

		console.printInfo("Initializing of look and feel... ");
		// Initialize look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			console.println("Done.");
		} catch (Throwable ignored) {
			try {
				console.println("Failed.");
				console.printError("Your java failed to provide normal look and feel, trying the old fallback now.. ");
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				console.println("Done.");
			} catch (Throwable t) {
				console.println("Failed.");
				console.error("MineUSB has crashed");
				console.error("Unexpected exception setting look and feel");
				t.printStackTrace();
				console.error("Force MineUSB to stop...");
				return;
			}
		}

		// Files
		binFolder = new File("bin/");
		dataFolder = new File("data/");
		
		if (!binFolder.exists())
			binFolder.mkdirs();
		
		if (!dataFolder.exists())
			dataFolder.mkdirs();

		// Launcher manager
		console.printInfo("Initializing launcher system... ");
		new LauncherManager();
		console.println("Done.");

		// Initialize frame
		frame = new MineUSBFrame();
		optionsFrame = new OptionsFrame();

		frame.setVisible(true);
		
		new TestFrame();

		// Add shutdown thread
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				console.info("MineUSB has stopped!");
			}
		});
	}

	public static OptionsFrame getOptionsFrame() {
		return optionsFrame;
	}

	public static String getVersion() {
		return version;
	}

	public static MineLogger getConsole() {
		return console;
	}

	public static OSType getOS() {
		return os;
	}
	
	public static File getBinFolder() {
		return binFolder;
	}
	
	public static File getDataFolder() {
		return dataFolder;
	}

	public static void shutdown() {
		if (frame != null) {
			frame.dispose();
		}
		if (optionsFrame != null) {
			optionsFrame.dispose();
		}
	}
}
