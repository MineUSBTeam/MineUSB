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
import fr.w67clement.core.system.OS;
import fr.w67clement.core.ui.dialogs.DownloadDialog;
import fr.w67clement.core.utils.Utils;

public class Skyolauncher extends Launcher {

	public Skyolauncher() {
		this.setName("Skyolauncher");
		this.setDescription("An flexible Minecraft launcher, make by Skyost.");
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		osCompatibility.add(OS.LINUX);
		this.setOSCompatibilityList(osCompatibility);
		this.setDownloadURL("http://files.skyost.eu/files/projects/Skyolauncher.jar");
		this.setFile(new File("bin/Skyolauncher.jar"));
	}

	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline("http://www.skyost.eu/skyolauncher/res/skyolauncher.png")) {
				//return new ImageIcon(
						//Toolkit.getDefaultToolkit()
								//.getImage(
										//new URL(
												//"http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
				try {
					return new ImageIcon(ImageIO.read(new URL("http://www.skyost.eu/skyolauncher/res/skyolauncher.png").openStream()).getScaledInstance(300, 75, 16));
				} catch (IOException e) {
				}
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
		DownloadDialog dialog = new DownloadDialog("Running launcher...",
				"Running Skyolauncher...",
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
				File scriptFile = new File("Skyolauncher.sh");
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
			} else if (OS.getCurrentPlatform() == OS.LINUX) {
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
