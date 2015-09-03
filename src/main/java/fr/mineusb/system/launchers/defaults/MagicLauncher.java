package fr.mineusb.system.launchers.defaults;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.utils.FileUtils;
import fr.w67clement.core.system.MineSystem;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.ui.dialogs.DownloadDialog;
import fr.w67clement.core.utils.Utils;

public class MagicLauncher extends Launcher {
	
	public MagicLauncher() {
		this.setName("MagicLauncher");
		this.setDescription("The MagicLauncher");
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		osCompatibility.add(OS.LINUX);
		osCompatibility.add(OS.SOLARIS);
		this.setOSCompatibilityList(osCompatibility);
		if (MineSystem.getCurrentPlatform() == OS.WINDOWS) {
			this.setDownloadURL("http://magiclauncher.com/download.php?f=MagicLauncher_1.3.1.jar");
			this.setFile(new File("bin/MagicLauncher.jar"));
		} else {
			this.setDownloadURL("http://magiclauncher.com/download.php?f=MagicLauncher_1.3.1.jar");
			this.setFile(new File("bin/MagicLauncher.jar"));
		}
	}
	
	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline("http://i.ytimg.com/vi/S4ZNrOSLWQk/0.jpg")) {
				// return new ImageIcon(
				// Toolkit.getDefaultToolkit()
				// .getImage(
				// new URL(
				// "http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
				try {
					return new ImageIcon(
							ImageIO.read(
									new URL(
											"http://i.ytimg.com/vi/S4ZNrOSLWQk/0.jpg")
											.openStream()).getScaledInstance(
									300, 75, 16));
				} catch (IOException e) {
					MineUSB.getConsole()
							.warning(
									"MineUSB can't load MagicLauncher logo, you running in offline mode?");
					return super.getImageIcon();
				}
			} else {
				MineUSB.getConsole()
						.warning(
								"MineUSB can't load MagicLauncher logo, you running in offline mode?");
				return super.getImageIcon();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return super.getImageIcon();
	}
	
	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog("Running launcher...",
				"Running MagicLauncher's Launcher...",
				Toolkit.getDefaultToolkit().getImage(
						MineUSB.class
								.getResource(MineUSBConstants.FAVICON_PATH)));
		dialog.setVisible(true);
		dialog.getProgressBar().setIndeterminate(true);

		if (!this.getFile().exists()) {
			dialog.setTitle("Downloading launcher...");
			dialog.setText("Downloading the launcher, please wait...");
			runSuccess = Utils.download(this.getDownloadURL_inString(),
					this.getFile(), dialog);
			dialog.setTitle("Running launcher...");
			dialog.setText("Running MagicLauncher's Launcher...");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OS.getCurrentPlatform() == OS.WINDOWS) {
				File scriptFile = new File("MagicLauncher.bat");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunJavaLauncherScriptWindows(scriptFile,
									"Launching MagicLauncher's Launcher",
									this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start MagicLauncher.bat");
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
				File scriptFile = new File("MagicLauncher.sh");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunLauncherScriptMac(scriptFile,
									"Launching MagicLauncher's Launcher",
									this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(new String[] { "sh", scriptFile.getPath() });
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.LINUX) {
				File scriptFile = new File("MagicLauncher.sh");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunLauncherScriptLinux(scriptFile,
									"Launching MagicLauncher's Launcher",
									this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(new String[] { "sh", scriptFile.getPath() });
				} catch (IOException e) {
					runSuccess = false;
				}
			}
		}

		dialog.dispose();
		return runSuccess;
	}

}
