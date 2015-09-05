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
import fr.mineusb.ui.panels.CreditsPanel;
import fr.mineusb.ui.panels.LaunchersPanel;
import fr.mineusb.ui.panels.MineUSBPanel;
import fr.mineusb.ui.panels.OptionsPanel;
import fr.w67clement.core.logger.MineLogger;
import fr.w67clement.core.logger.MineLoggerManager;
import fr.w67clement.core.system.MineSystem;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.utils.Utils;

public class MineUSB {

	private static final String version = "Beta 1.0.3";
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
	// Utils
	private static boolean java_design = false;

	public static void main(String[] args) {
		// Initialize MineUSBLogger
		MineLoggerManager.registerLogger("MineUSB");
		console = MineLoggerManager.getLogger("MineUSB");
		// Starting MineUSB
		console.info("Starting MineUSB " + version);
		os = OS.getCurrentPlatform();
		console.info("OS used: " + MineSystem.getCurrentOS());

		System.setProperty("java.net.preferIPv4Stack", "true");

		for (String str : args) {
			if (str.equalsIgnoreCase("--java_design")) {
				java_design = true;
			}
		}

		console.printInfo("Initializing of look and feel... ");
		// Initialize look and feel
		if (!java_design) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
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

		} else
			console.println("Use Java look and feel.");
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
		try {
			LauncherManager.getLauncher(config.getLauncherType());
		} catch (LauncherNotFoundException e) {
			console.error("The launcher '" + config.getLauncherType()
					+ "' was not exists! Reset the configuration...");
			config.setLauncherType("Minecraft");
			MineUSBConfig.reloadConfiguration();
		}

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

	public static boolean hasJavaDesign() {
		return java_design;
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
		// Selected launcher
		MineUSBPanel.getSelectedLauncherLabel().setText(
				"<html>" + lang.getSelectedLauncherLabel()
						+ "<b><font color=\"#00CC00\">"
						+ MineUSB.getLauncherUsed().getName()
						+ "</font></b></html>");
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
		// Credits
		CreditsPanel.getCreditsLabel().setText(
				"<html><b>" + lang.getCreditsText() + "</b></html>");
		CreditsPanel.getW67clementLabel().setText(
				"<html>- <b><font color=\"#00CC00\">w67clement</font></b>: "
						+ lang.getW67clementDescription() + ".<html>");
		CreditsPanel.getXoliderLabel().setText(
				"<html>- <b><font color=\"#00CC00\">xolider</font></b>: "
						+ lang.getXoliderDescription() + ".<html>");
		CreditsPanel.getMentor6561Label().setText(
				"<html>- <b><font color=\"#00CC00\">mentor6561</font></b>: "
						+ lang.getMentor6561Description() + ".</html>");
		// Special thanks to
		CreditsPanel.getSpecialThanksLabel().setText(
				"<html><b>" + lang.getSpecialThanksToText() + "</b></html>");
		CreditsPanel.getTheShark34Label().setText(
				"<html>- <b><font color=\"#00CC00\">TheShark34</font></b>: "
						+ lang.getTheShark34Description() + ".</html>");
		CreditsPanel.getSupportersLabel().setText(
				"<html><b>" + lang.getSupportersText() + ".</b></html>");
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
		String os = "";
		for (int i = 0; i < launcher.getOSCompatibilityList().size(); i++) {
			OS os2 = launcher.getOSCompatibilityList().get(i);
			if (i == 0) {
				os = os + os2.getOSName();
			} else
				os = os + ", " + os2.getOSName();
		}
		// Selected launcher
		MineUSBPanel.getSelectedLauncherLabel().setText(
				"<html>" + MineUSB.getLangUsed().getSelectedLauncherLabel()
						+ "<b><font color=\"#00CC00\">" + launcher.getName()
						+ "</font></b></html>");
		LaunchersPanel.getLauncherInformations().setText(
				"<html><b>Launcher: </b><font color=\"#00CC00\">"
						+ launcher.getName() + "</font><br />"
						+ "<b>Description: </b><font color=\"#00CC00\">"
						+ launcher.getDescription() + "</font><br />"
						+ "<b>OS: </b><font color=\"#00CC00\">" + os
						+ "</font></html>");
		LaunchersPanel.getLauncherLogo().setIcon(launcher.getImageIcon());
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
