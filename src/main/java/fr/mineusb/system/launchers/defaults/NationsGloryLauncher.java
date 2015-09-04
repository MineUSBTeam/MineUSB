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

public class NationsGloryLauncher extends Launcher {

	public NationsGloryLauncher() {
		this.setName("NationsGlory");
		this.setDescription("Launcher of NationsGlory.");
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		osCompatibility.add(OS.LINUX);
		osCompatibility.add(OS.SOLARIS);
		this.setOSCompatibilityList(osCompatibility);
		if (MineSystem.getCurrentPlatform() == OS.WINDOWS) {
			this.setDownloadURL("https://launcher.nationsglory.fr/NationsGlory.exe");
			this.setFile(new File("bin/NationsGlory.exe"));
		} else {
			this.setDownloadURL("https://launcher.nationsglory.fr/NationsGlory.jar");
			this.setFile(new File("bin/NationsGlory.jar"));
		}
	}

	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline("https://www.nationsglory.fr/theme/site/img/logo.png")) {
				// return new ImageIcon(
				// Toolkit.getDefaultToolkit()
				// .getImage(
				// new URL(
				// "http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
				try {
					return new ImageIcon(
							ImageIO.read(
									new URL(
											"https://www.nationsglory.fr/theme/site/img/logo.png")
											.openStream()).getScaledInstance(
									300, 75, 16));
				} catch (IOException e) {
					MineUSB.getConsole()
							.warning(
									"MineUSB can't load NationsGlory logo, you running in offline mode?");
					return super.getImageIcon();
				}
			} else {
				MineUSB.getConsole()
						.warning(
								"MineUSB can't load NationsGlory logo, you running in offline mode?");
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
				"Running NationsGlory's Launcher...",
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
			dialog.setText("Running NationsGlory's Launcher...");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OS.getCurrentPlatform() == OS.WINDOWS) {
				File scriptFile = new File("NationsGlory.bat");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunJavaLauncherScriptWindows(scriptFile,
									"Launching NationsGlory's Launcher",
									this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start NationsGlory.bat");
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
				File scriptFile = new File("NationsGlory_mac.sh");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunLauncherScriptMac(scriptFile,
									"Launching NationsGlory's Launcher",
									this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(new String[] { "sh", scriptFile.getPath() });
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.LINUX) {
				File scriptFile = new File("NationsGlory.sh");
				if (!scriptFile.exists()) {
					FileUtils
							.generateRunLauncherScriptLinux(scriptFile,
									"Launching NationsGlory's Launcher",
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
