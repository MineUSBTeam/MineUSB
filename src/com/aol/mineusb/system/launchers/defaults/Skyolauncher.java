package com.aol.mineusb.system.launchers.defaults;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.aol.mineusb.MineUSB;
import com.aol.mineusb.system.launchers.Launcher;
import com.aol.mineusb.utils.FileUtils;
import com.aol.w67clement.mineapi.enums.OSType;
import com.aol.w67clement.mineapi.frame.dialogs.DownloadDialog;
import com.aol.w67clement.mineapi.utils.Utils;

public class Skyolauncher extends Launcher {

	public Skyolauncher() {
		this.setName("Skyolauncher");
		this.setDescription("A flexible Minecraft launcher, make by Skyost.");
		// OS compatibility
		List<OSType> osCompatibility = new ArrayList<OSType>();
		osCompatibility.add(OSType.WINDOWS);
		osCompatibility.add(OSType.MAC_OSX);
		osCompatibility.add(OSType.LINUX);
		this.setOSCompatibilityList(osCompatibility);
		this.setDownloadURL("http://files.skyost.eu/files/projects/Skyolauncher.jar");
		this.setFile(new File("bin/Skyolauncher.jar"));
	}

	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline("http://www.skyost.eu/skyolauncher/res/skyolauncher.png")) {
				return new ImageIcon(
						Toolkit.getDefaultToolkit()
								.getImage(
										new URL(
												"http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
			} else {
				return super.getImageIcon();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return super.getImageIcon();
	}

	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog(
				"Running launcher...",
				"Running Skyolauncher...",
				Toolkit.getDefaultToolkit()
						.getImage(
								MineUSB.class
										.getResource("/com/aol/mineusb/res/favicon.png")));
		dialog.setVisible(true);
		dialog.getProgressBar().setIndeterminate(true);

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
			if (OSType.getCurrentPlatform() == OSType.WINDOWS) {
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
			} else if (OSType.getCurrentPlatform() == OSType.MAC_OSX) {
				File scriptFile = new File("Skyolauncher.command");
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
			} else if (OSType.getCurrentPlatform() == OSType.LINUX) {
				File scriptFile = new File("Skyolauncher.sh");
				if (!scriptFile.exists()) {
					FileUtils.generateRunLauncherScriptLinux(scriptFile,
							"Launching Skyolauncher", this.getFile());
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
