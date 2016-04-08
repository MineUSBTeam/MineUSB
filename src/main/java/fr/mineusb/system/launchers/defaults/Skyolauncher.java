package fr.mineusb.system.launchers.defaults;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.utils.FileUtils;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.ui.dialogs.DownloadDialog;
import fr.w67clement.core.utils.Utils;

public class Skyolauncher extends Launcher {

	public Skyolauncher() {
		this.setName("Skyolauncher");
		this.setDescription("A flexible Minecraft launcher, made by Skyost.");
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		osCompatibility.add(OS.LINUX);
		osCompatibility.add(OS.SOLARIS);
		this.setOSCompatibilityList(osCompatibility);
		this.setDownloadURL("http://files.skyost.eu/files/projects/Skyolauncher.jar");
		this.setFile(new File("bin/Skyolauncher.jar"));
	}

	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline("https://www.skyost.eu/skyolauncher/res/skyolauncher.png")) {
				// return new ImageIcon(
				// Toolkit.getDefaultToolkit()
				// .getImage(
				// new URL(
				// "http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
				try {
					InputStream imgStream = new URL(
							"https://www.skyost.eu/skyolauncher/res/skyolauncher.png")
							.openStream();
					if (imgStream != null) {
						return new ImageIcon(ImageIO.read(imgStream)
								.getScaledInstance(300, 75, 16));
					} else
						return super.getImageIcon();
				} catch (IOException e) {
					MineUSB.getConsole()
							.warn(
									"MineUSB can't load Skyolauncher logo, you running in offline mode?");
					return super.getImageIcon();
				} catch (NullPointerException e) {
					MineUSB.getConsole()
							.warn(
									"MineUSB can't load Skyolauncher logo, you running in offline mode?");
					return super.getImageIcon();
				}
			} else {
				MineUSB.getConsole()
						.warn(
								"MineUSB can't load Skyolauncher logo, you running in offline mode?");
				return super.getImageIcon();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return super.getImageIcon();
	}

	@SuppressWarnings("resource")
	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog("Running launcher...",
				"Running Skyolauncher...",
				Toolkit.getDefaultToolkit().getImage(
						MineUSB.class
								.getResource(MineUSBConstants.FAVICON_PATH)));
		dialog.setVisible(true);
		dialog.getProgressBar().setIndeterminate(true);
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		if (!this.getFile().exists()) {
			dialog.setTitle("Downloading launcher...");
			dialog.setText("Downloading the launcher, please wait...");
			runSuccess = Utils.download(this.getDownloadURL_inString(),
					this.getFile(), dialog);
			dialog.setTitle("Running launcher...");
			dialog.setText("Running Skyolauncher...");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OS.getCurrentPlatform() == OS.WINDOWS) {
				File scriptFile = new File("Skyolauncher.bat");
				if (!scriptFile.exists()) {
					FileUtils.generateRunJavaLauncherScriptWindows(scriptFile,
							"Launching Skyolauncher", this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start Skyolauncher.bat");
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
				File scriptFile = new File("Skyolauncher_mac.sh");
				if (!scriptFile.exists()) {
					FileUtils.generateRunLauncherScriptMac(scriptFile,
							"Launching Skyolauncher", this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(new String[] { "sh", scriptFile.getPath() });
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.LINUX
					|| OS.getCurrentPlatform() == OS.SOLARIS) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					System.setProperty("user.home", MineUSB.getDataFolder()
							.getAbsolutePath());
					Class<?> skyolauncherClass = new URLClassLoader(
							new URL[] { this.getFile().toURI().toURL() })
							.loadClass("fr.skyost.launcher.Skyolauncher");
					Constructor<?> constructor = skyolauncherClass
							.getConstructor(new Class<?>[] {});
					Method mainMethod = skyolauncherClass.getMethod("main",
							new Class<?>[] { String[].class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod.invoke(constructor.newInstance(new Object[] {}),
							new Object[] { new String[] { "-console" } });
				} catch (Throwable e) {
					e.printStackTrace();
					runSuccess = false;
				}
			}
		}

		dialog.dispose();
		return runSuccess;
	}
}
