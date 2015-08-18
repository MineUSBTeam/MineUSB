package fr.mineusb;

import java.io.File;
import java.net.MalformedURLException;

import javax.swing.UIManager;

import fr.mineusb.exceptions.LangNotFoundException;
import fr.mineusb.exceptions.LauncherNotFoundException;
import fr.mineusb.system.MineUSBConfig;
import fr.mineusb.system.lang.Lang;
import fr.mineusb.system.lang.LangManager;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.system.launchers.LauncherManager;
import fr.mineusb.ui.LicenseFrame;
import fr.mineusb.ui.MineUSBFrame;
import fr.mineusb.ui.OptionsFrame;
import fr.mineusb.ui.panels.LaunchersPanel;
import fr.mineusb.ui.panels.MineUSBPanel;
import fr.mineusb.ui.panels.OptionsPanel;
import fr.w67clement.core.logger.MineLogger;
import fr.w67clement.core.logger.MineLoggerManager;
import fr.w67clement.core.system.MineSystem;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.utils.Utils;

public class MineUSB {

	private static final String version = "Beta 1.0.2";
	// Logger
	private static MineLogger console;
	// Configuration
	private static MineUSBConfig config;
	// Frame
	private static MineUSBFrame frame;
	private static OptionsFrame optionsFrame;
	private static LicenseFrame licenseFrame;
	// Computer's OS
	private static OS os;
	// Files
	private static File binFolder;
	private static File dataFolder;
	private static File mineusbFolder;

	public static void main(String[] args) {
		// Initialize MineUSBLogger
		MineLoggerManager.registerLogger("MineUSB");
		console = MineLoggerManager.getLogger("MineUSB");
		// Starting MineUSB
		console.info("Starting MineUSB " + version);
		os = OS.getCurrentPlatform();
		console.info("OS used: " + MineSystem.getCurrentOS());

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
		mineusbFolder = new File(dataFolder, "MineUSB/");

		if (!binFolder.exists())
			binFolder.mkdirs();

		if (!dataFolder.exists())
			dataFolder.mkdirs();

		if (!mineusbFolder.exists())
			mineusbFolder.mkdirs();

		// Launcher manager
		console.printInfo("Initializing launcher and lang system... ");
		new LauncherManager();
		new LangManager();
		console.println("Done.");

		// Configuration
		config = new MineUSBConfig(new File(mineusbFolder, "config.properties"));

		// Initialize frame
		new Thread() {
			@Override
			public void run() {
				licenseFrame = new LicenseFrame();
			}
		}.start();
		frame = new MineUSBFrame();
		optionsFrame = new OptionsFrame();

		frame.setVisible(true);

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

	public static LicenseFrame getLicenseFrame() {
		return licenseFrame;
	}

	public static MineUSBConfig getConfig() {
		return config;
	}

	public static String getVersion() {
		return version;
	}

	public static MineLogger getConsole() {
		return console;
	}

	public static OS getOS() {
		return os;
	}

	public static File getBinFolder() {
		return binFolder;
	}

	public static File getDataFolder() {
		return dataFolder;
	}

	public static Lang getLangUsed() {
		try {
			return LangManager.getLang(config.getLang());
		} catch (LangNotFoundException e) {
			e.printStackTrace();
		}
		try {
			return LangManager.getLang("ENGLISH");
		} catch (LangNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Launcher getLauncherUsed() {
		try {
			return LauncherManager.getLauncher(config.getLauncherType());
		} catch (LauncherNotFoundException e) {
			e.printStackTrace();
		}
		try {
			return LauncherManager.getLauncher("Minecraft");
		} catch (LauncherNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void applyLang(final Lang lang) {
		// Description
		MineUSBPanel.getDescriptionLabel().setText(lang.getDescriptionLabel());
		// Play Button
		MineUSBPanel.getPlayButton().setText(lang.getPlayButton());
		// Options Button
		MineUSBPanel.getOptionsButton().setText(lang.getOptionsButton());
		// Quit Button
		MineUSBPanel.getQuitButton().setText(lang.getQuitButton());
		// Authors label
		MineUSBPanel
				.getAuthorsLabel()
				.setText(
						"<html>"
								+ lang.getAuthorsLabel()
										.replace("<author>",
												"<font color=\"#00CC00\">67clement</font>")
										.replace(
												"<contributors>",
												"<font color=\"#00CC00\">xolider</font>,"
														+ " <font color=\"#00CC00\">mentor6561</font>")
								+ "</html>");
		// View License
		OptionsPanel.getViewLicenseButton()
				.setText(lang.getViewLicenseButton());
		// Change lang label
		OptionsPanel.getChangLangLabel().setText(
				"<html><br /><b>" + lang.getChangeLangLabel() + "</b></html>");
		OptionsPanel.getLangList().setSelectedItem(lang.getName());
		// Reload Button
		OptionsPanel.getReloadButton().setText(lang.getReloadButton());
		// Quit Button
		OptionsPanel.getQuitButton().setText(lang.getQuitButton());
		// Websites online label
		OptionsPanel.getWebsitesOnlineLabel().setText(
				"<html><b>" + lang.getWebsitesOnline() + "</b></html>");
		// Skins server
		OptionsPanel.getSkinsServerOnlineLabel().setText(
				"<html>&nbsp;" + lang.getLoadingText()
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
		OptionsPanel.getMultiplayerSessionOnlineLabel().setText(
				"<html>&nbsp;" + lang.getLoadingText()
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
		new Thread() {
			public void run() {
				try {
					if (Utils.isOnline("https://skins.minecraft.net")) {
						OptionsPanel
								.getSkinsServerOnlineLabel()
								.setText(
										"<html>"
												+ MineUSB.getLangUsed()
														.getSkinsServerLabel()
												+ " <font color=\"#00CC00\">\u2714</font</html>");
					} else {
						OptionsPanel
								.getSkinsServerOnlineLabel()
								.setText(
										"<html>"
												+ MineUSB.getLangUsed()
														.getSkinsServerLabel()
												+ " <font color=\"#FF0000\">✖</font</html>");
					}
				} catch (MalformedURLException e) {
					OptionsPanel
							.getSkinsServerOnlineLabel()
							.setText(
									"<html>"
											+ MineUSB.getLangUsed()
													.getSkinsServerLabel()
											+ " <font color=\"#FF0000\">✖</font</html>");
				}
			}
		}.start();
		new Thread() {
			public void run() {
				// Multiplayer session
				try {
					if (Utils.isOnline("https://sessionserver.mojang.com")) {
						OptionsPanel
								.getMultiplayerSessionOnlineLabel()
								.setText(
										"<html>"
												+ lang.getMultiplayerSessionLabel()
												+ " <font color=\"#00CC00\">\u2714</font</html>");
					} else {
						OptionsPanel
								.getMultiplayerSessionOnlineLabel()
								.setText(
										"<html>"
												+ lang.getMultiplayerSessionLabel()
												+ " <font color=\"#FF0000\">✖</font</html>");
					}
				} catch (MalformedURLException e) {
					OptionsPanel
							.getMultiplayerSessionOnlineLabel()
							.setText(
									"<html>"
											+ lang.getMultiplayerSessionLabel()
											+ " <font color=\"#FF0000\">✖</font</html>");
				}
			}
		}.start();
	}

	public static void applyLauncher(Launcher launcher) {
		LaunchersPanel.getLauncherLogo().setIcon(launcher.getImageIcon());
		String os = "";
		for (int i = 0; i < launcher.getOSCompatibilityList().size(); i++) {
			OS os2 = launcher.getOSCompatibilityList().get(i);
			if (i == 0) {
				os = os + os2.getOSName();
			} else
				os = os + ", " + os2.getOSName();
		}
		LaunchersPanel.getLauncherInformations().setText(
				"<html><b>Launcher: </b><font color=\"#00CC00\">"
						+ launcher.getName() + "</font><br />"
						+ "<b>Description: </b><font color=\"#00CC00\">"
						+ launcher.getDescription() + "</font><br />"
						+ "<b>OS: </b><font color=\"#00CC00\">" + os
						+ "</font></html>");
	}

	public static void shutdown() {
		if (frame != null) {
			frame.dispose();
		}
		if (optionsFrame != null) {
			optionsFrame.dispose();
		}
		if (licenseFrame != null) {
			licenseFrame.dispose();
		}
	}
}
